package com.apps.pyr.pyrtools.main.cards.image;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.apps.pyr.pyrtools.R;
import com.apps.pyr.pyrtools.core.android.ui.cards.CardViewHolder;
import com.squareup.picasso.Picasso;

public class ImageHolder extends CardViewHolder<ImageCard> {
  private ImageView image;
  private TextView name;

  public ImageHolder(ViewGroup parent) {
    super(parent, R.layout.card_image);
    loadViews(itemView);
  }

  @Override public void fillWith(ImageCard card) {
    itemView.setTag(card);
    Picasso.with(itemView.getContext()).load(card.getUrl()).into(image);
    name.setText(card.getName());
  }

  private void loadViews(View parent) {
    image = (ImageView) parent.findViewById(R.id.image);
    name = (TextView) parent.findViewById(R.id.name);
  }
}
