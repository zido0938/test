package com.goldstone.saboteur_backend.domain;

import com.goldstone.saboteur_backend.domain.card.Card;
import com.goldstone.saboteur_backend.domain.card.CardDeck;
import com.goldstone.saboteur_backend.domain.card.PathCard;
import com.goldstone.saboteur_backend.domain.enums.GameRole;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Game {
    private String id;
    private List<Player> players;
    private Board board;
    private CardDeck deck;
    private int currentPlayerIndex;
    private boolean isGoldFound;
    private Map<String, Integer> goldNuggets; // 플레이어별 금덩이 수
    private List<GameStateChangeListener> listeners;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.players = new ArrayList<>();
        this.board = new Board();
        this.deck = new CardDeck();
        this.currentPlayerIndex = 0;
        this.isGoldFound = false;
        this.goldNuggets = new HashMap<>();
        this.listeners = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
        goldNuggets.put(player.getId(), 0);
        firePlayerAdded(player);
    }

    public void start() {
        if (players.size() < 3 || players.size() > 10) {
            throw new IllegalStateException("게임은 3명에서 10명 사이의 플레이어가 필요합니다.");
        }

        // 역할 카드 분배
        distributeRoles();

        // 초기 카드 분배
        dealInitialCards();

        // 첫 플레이어 설정
        currentPlayerIndex = 0;

        fireGameStarted();
    }

    private void distributeRoles() {
        List<GameRole> roles = new ArrayList<>();

        // 플레이어 수에 따라 역할 카드 생성
        int goldDiggers = 0;
        int saboteurs = 0;

        if (players.size() <= 5) {
            goldDiggers = players.size() - 2;
            saboteurs = 2;
        } else if (players.size() <= 7) {
            goldDiggers = players.size() - 3;
            saboteurs = 3;
        } else {
            goldDiggers = players.size() - 4;
            saboteurs = 4;
        }

        // 역할 추가
        for (int i = 0; i < goldDiggers; i++) {
            roles.add(GameRole.GOLD_DIGGER);
        }

        for (int i = 0; i < saboteurs; i++) {
            roles.add(GameRole.SABOTEUR);
        }

        // 역할 섞기
        Collections.shuffle(roles);

        // 플레이어에게 역할 분배
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            player.setRole(roles.get(i));
        }
    }

    private void dealInitialCards() {
        // 카드 덱 섞기
        deck.shuffle();

        // 각 플레이어에게 카드 분배
        for (Player player : players) {
            for (int i = 0; i < 6; i++) {
                Card card = deck.drawCard();
                if (card != null) {
                    player.addCardToHand(card);
                }
            }
        }
    }

    public boolean canPlayCard(Player player, Card card, Position position) {
        // 플레이어가 현재 플레이어인지 확인
        if (!players.get(currentPlayerIndex).getId().equals(player.getId())) {
            return false;
        }

        // 플레이어가 핸디캡이 있는지 확인
        if (player.isHandicapped()) {
            return false;
        }

        // 카드가 플레이어의 손에 있는지 확인
        if (player.getCard(card.getId()) == null) {
            return false;
        }

        // 카드 타입에 따라 다른 검증 로직 적용
        if (card instanceof PathCard) {
            return board.canPlaceCard((PathCard) card, position);
        }

        // 액션 카드 등 다른 카드 타입에 대한 검증 로직 추가

        return true;
    }

    public void playCard(Player player, Card card, Position position) {
        // 카드를 플레이할 수 있는지 확인
        if (!canPlayCard(player, card, position)) {
            throw new IllegalStateException("이 카드를 플레이할 수 없습니다.");
        }

        // 카드 타입에 따라 다른 로직 적용
        if (card instanceof PathCard) {
            board.placeCard((PathCard) card, position);

            // 골드에 도달했는지 확인
            if (board.isGoldReached()) {
                isGoldFound = true;
                distributeGoldReward();
                fireGameEnded();
            }
        }

        // 플레이어의 손에서 카드 제거
        player.removeCardFromHand(card);

        // 새 카드 드로우
        Card newCard = deck.drawCard();
        if (newCard != null) {
            player.addCardToHand(newCard);
        }

        fireCardPlayed(player, card, position);

        // 다음 플레이어로 턴 넘김
        nextTurn();
    }

    public void playActionCard(Player player, Card card, Player targetPlayer) {
        // 액션 카드 처리 로직
        // 구현 필요

        // 플레이어의 손에서 카드 제거
        player.removeCardFromHand(card);

        // 새 카드 드로우
        Card newCard = deck.drawCard();
        if (newCard != null) {
            player.addCardToHand(newCard);
        }

        fireActionCardPlayed(player, card, targetPlayer);

        // 다음 플레이어로 턴 넘김
        nextTurn();
    }

    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        fireNextTurn();

        // 게임 종료 조건 확인
        if (deck.isEmpty() && !isGoldFound) {
            fireGameEnded();
        }
    }

    private void distributeGoldReward() {
        // 골드를 찾은 플레이어에게 보상 지급
        Player currentPlayer = players.get(currentPlayerIndex);

        // 역할에 따라 보상 분배
        for (Player player : players) {
            if (player.getRole() == GameRole.GOLD_DIGGER) {
                // 금광을 찾은 플레이어가 금광 발굴자인 경우
                int nuggets = goldNuggets.getOrDefault(player.getId(), 0);
                if (player.getId().equals(currentPlayer.getId())) {
                    nuggets += 3; // 금광을 찾은 발굴자는 3개
                } else {
                    nuggets += 2; // 다른 발굴자는 2개
                }
                goldNuggets.put(player.getId(), nuggets);
            }
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public boolean isGameOver() {
        return isGoldFound || deck.isEmpty();
    }

    // 이벤트 리스너 관련 메서드
    public void addListener(GameStateChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameStateChangeListener listener) {
        listeners.remove(listener);
    }

    protected void fireGameStarted() {
        for (GameStateChangeListener listener : listeners) {
            listener.onGameStarted(this);
        }
    }

    protected void firePlayerAdded(Player player) {
        for (GameStateChangeListener listener : listeners) {
            listener.onPlayerAdded(this, player);
        }
    }

    protected void fireCardPlayed(Player player, Card card, Position position) {
        for (GameStateChangeListener listener : listeners) {
            listener.onCardPlayed(this, player, card, position);
        }
    }

    protected void fireActionCardPlayed(Player player, Card card, Player targetPlayer) {
        for (GameStateChangeListener listener : listeners) {
            listener.onActionCardPlayed(this, player, card, targetPlayer);
        }
    }

    protected void fireNextTurn() {
        for (GameStateChangeListener listener : listeners) {
            listener.onNextTurn(this);
        }
    }

    protected void fireGameEnded() {
        for (GameStateChangeListener listener : listeners) {
            listener.onGameEnded(this);
        }
    }

    // 게임 상태 변경 리스너 인터페이스
    public interface GameStateChangeListener {
        void onGameStarted(Game game);
        void onPlayerAdded(Game game, Player player);
        void onCardPlayed(Game game, Player player, Card card, Position position);
        void onActionCardPlayed(Game game, Player player, Card card, Player targetPlayer);
        void onNextTurn(Game game);
        void onGameEnded(Game game);
    }
}
