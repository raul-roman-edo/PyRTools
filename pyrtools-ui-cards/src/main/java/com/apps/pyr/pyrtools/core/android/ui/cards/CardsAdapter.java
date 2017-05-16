package com.apps.pyr.pyrtools.core.android.ui.cards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsAdapter extends RecyclerView.Adapter<CardViewHolder> {
  public static final int PRELOAD_THRESHOLD = 7;
  private static final String HOLDERS_MESSAGE =
      "Please, set a non null holder creators map to the adapter before continue";
  private static final int INVALID_POSITION = -1;
  private final List<Card> cards;
  private final RecyclerViewEndProcessor scrollEndProcessor;
  private Map<Integer, CardViewHolderCreator> holderCreatorsMap;

  public CardsAdapter(final RecyclerView recyclerView) {
    cards = new ArrayList<>();
    scrollEndProcessor = new RecyclerViewEndProcessor(PRELOAD_THRESHOLD);
    recyclerView.addOnScrollListener(scrollEndProcessor);
    recyclerView.addOnChildAttachStateChangeListener(
        new RecyclerView.OnChildAttachStateChangeListener() {
          @Override public void onChildViewAttachedToWindow(View view) {
            CardViewHolder holder = (CardViewHolder) recyclerView.getChildViewHolder(view);
            holder.onAttached();
          }

          @Override public void onChildViewDetachedFromWindow(View view) {
            CardViewHolder holder = (CardViewHolder) recyclerView.getChildViewHolder(view);
            holder.onDetached();
          }
        });
  }

  public void setOnNearBottomEventListener(
      RecyclerViewEndProcessor.OnNearBottomEventListener onNearBottomEventListener) {
    scrollEndProcessor.setOnNearBottomEventListener(onNearBottomEventListener);
  }

  public void setBottomProcessed() {
    scrollEndProcessor.setBottomProcessed();
  }

  @Override public int getItemViewType(int position) {
    if (areThereNoCards()) {
      return 0;
    }

    Card card = cards.get(position);
    return card.getType();
  }

  @Override public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (areThereNoCards()) return null;
    if (holderCreatorsMap == null) {
      throw new RuntimeException(HOLDERS_MESSAGE);
    }
    CardViewHolderCreator creator = holderCreatorsMap.get(viewType);
    if (creator == null) {
      return null;
    } else {
      return creator.create(parent);
    }
  }

  @Override public void onBindViewHolder(CardViewHolder rawHolder, int position) {
    rawHolder.fillWith(cards.get(position));
  }

  @Override public int getItemCount() {
    return cards.size();
  }

  public void setHolderCreatorsMap(Map<Integer, CardViewHolderCreator> holderCreatorsMap) {
    this.holderCreatorsMap = holderCreatorsMap;
  }

  private boolean areThereNoCards() {
    return cards.isEmpty();
  }

  public List<Card> getItems() {
    return cards;
  }

  public Card getItemByPosition(int position) {
    Card card = cards.get(position);

    return card;
  }

  public int findPositionById(Card toFind) {
    int position = cards.indexOf(toFind);

    return position;
  }

  public void addToPosition(Card newer, int newPosition) {
    cards.add(newPosition, newer);
    notifyItemInserted(newPosition);
  }

  public void addToBottom(Card card) {
    if (cards == null) {
      return;
    }
    int count = cards.size();
    cards.add(count, card);
    notifyItemRangeInserted(count, 1);
  }

  public void addToBottom(List<Card> cards) {
    if (this.cards == null || cards == null) {
      return;
    }
    int count = this.cards.size();
    this.cards.addAll(count, cards);
    notifyItemRangeInserted(count, cards.size());

    notifyItemChanged(this.cards.size());
  }

  public void update(List<Card> newCards) {
    if (cards.isEmpty()) {
      addToBottom(newCards);
    } else {
      addNew(newCards, false);
      clean(newCards);
    }
  }

  public void refresh(List<Card> newCards) {
    List<Card> oldCards = new ArrayList<>(cards);
    cards.clear();
    cards.addAll(newCards);
    for (Card newCard : newCards) {
      int currentPosition = oldCards.indexOf(newCard);
      int newPosition = newCards.indexOf(newCard);
      boolean isValidCard = currentPosition > -1 && newPosition > -1;
      boolean notSamePosition = newPosition != currentPosition;
      if (isValidCard) {
        if (notSamePosition) {
          notifyItemMoved(currentPosition, newPosition);
        } else {
          notifyItemChanged(currentPosition);
        }
      }
    }
  }

  public void removeAll() {
    if (cards.size() > 0) {
      notifyItemRangeRemoved(0, cards.size());
      cards.clear();
    }
  }

  public int remove(Card card) {
    if (cards.size() > 0) {
      int pos = cards.indexOf(card);
      cards.remove(pos);
      notifyItemRemoved(pos);
      return pos;
    }
    return INVALID_POSITION;
  }

  public void notifyItemChanged(Card card) {
    if (cards != null) {
      int position = cards.indexOf(card);
      notifyItemChanged(position);
    }
  }

  public void refreshFromPosition(int position) {
    int size = cards.size();
    if (position < size) {
      int itemsLeft = size - position;
      notifyItemRangeChanged(position, itemsLeft);
    }
  }

  protected void addNew(List<Card> newCards, boolean addToBottom) {
    int size = newCards.size();
    Card newer;
    for (int i = 0; i < size; i++) {
      newer = newCards.get(i);
      boolean mustNotAdd = cards.contains(newer);
      if (mustNotAdd) continue;

      int newPosition = obtainCurrentPosition(newCards, i, addToBottom);
      addToPosition(newer, newPosition);
    }
  }

  private int obtainCurrentPosition(List<Card> newCards, int i, boolean addToBottom) {
    Card previous;
    int newPosition = -1;
    if (addToBottom) {
      newPosition = cards.size() - 1;
    }
    for (int j = i - 1; j >= 0; j--) {
      previous = newCards.get(j);
      if (cards.contains(previous)) {
        newPosition = cards.lastIndexOf(previous);
        if (addToBottom) {
          newPosition = Math.max(cards.size() - 1, newPosition);
        }
        break;
      }
    }
    newPosition++;
    return newPosition;
  }

  private void clean(List<Card> newCards) {
    int position = 0;
    while (position < cards.size()) {
      Card current = cards.get(position);
      if (!newCards.contains(current)) {
        remove(current);
      } else {
        position++;
      }
    }
  }
}