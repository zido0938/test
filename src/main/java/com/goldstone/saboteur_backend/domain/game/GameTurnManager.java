package com.goldstone.saboteur_backend.domain.game;

import com.goldstone.saboteur_backend.domain.card.Card;
import com.goldstone.saboteur_backend.domain.mapping.UserGameRoom;
import com.goldstone.saboteur_backend.domain.user.User;
import com.goldstone.saboteur_backend.domain.user.UserCardDeck;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class GameTurnManager {
    private GameRoom gameRoom;
    private List<UserGameRoom> userGameRooms;
    private GameCardPool cardPool;
    private Map<User, UserCardDeck> userCardDecks;
    private int currentTurnIndex = 0;

    public GameTurnManager(
            GameRoom gameRoom, List<UserGameRoom> userGameRooms, GameCardPool cardPool) {
        this.gameRoom = gameRoom;
        this.userGameRooms = userGameRooms;
        this.cardPool = cardPool;
        this.userCardDecks = new HashMap<>();

        int cardsPerPlayer = getCardsPerPlayer(userGameRooms.size());
        for (UserGameRoom ugr : userGameRooms) {
            User user = ugr.getUser();
            List<Card> cards = new ArrayList<>();
            for (int i = 0; i < cardsPerPlayer; i++) {
                cards.add(cardPool.drawCard());
            }
            userCardDecks.put(user, new UserCardDeck(user, cards)); //각 플레이어가 카드풀에서 카드를 뽑아 UserCardDeck에 저장
        }
    }

    //사람수에따라 첫 분배 카드 수 조정
    private int getCardsPerPlayer(int playerCount) {
        if (playerCount >= 3 && playerCount <= 5) return 6;
        if (playerCount >= 6 && playerCount <= 7) return 5;
        if (playerCount >= 8 && playerCount <= 10) return 4;
        return 0;
    }

    public User getCurrentTurnUser() {
        return userGameRooms.get(currentTurnIndex).getUser();  // 현재 턴 유저 반환
    }

    /**
     * 현재 턴 플레이어가 카드를 사용(또는 버림)할 때 호출됩니다.
     * 현재 턴 플레이어의 손에 해당 카드가 있으면 제거하고 true 반환.
     * else(카드가 없으면) - false 반환.
     */
    public boolean playCard(Card card) {
        User currentUser = getCurrentTurnUser();
        UserCardDeck deck = userCardDecks.get(currentUser);
        if (deck != null && deck.getCards().contains(card)) {
            deck.getCards().remove(card);
            return true;
        }
        return false;
    }

    // 턴 종료 직전에 카드 드로우를 자동으로 처리
    public User nextTurn() {
        User currentUser = getCurrentTurnUser();
        UserCardDeck deck = userCardDecks.get(currentUser);

        // 턴 종료 직전에 카드 드로우 (덱이 남아있을 때)
        if (!cardPool.isEmpty()) {
            deck.getCards().add(cardPool.drawCard());
        }

        // 다음 플레이어로 이동
        currentTurnIndex = (currentTurnIndex + 1) % userGameRooms.size();
        return getCurrentTurnUser();
    }
}
