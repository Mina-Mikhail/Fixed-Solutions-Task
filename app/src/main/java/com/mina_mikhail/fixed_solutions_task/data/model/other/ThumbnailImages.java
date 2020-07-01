package com.mina_mikhail.fixed_solutions_task.data.model.other;

public class ThumbnailImages {

  private String image;
  private int drawable;
  private boolean isSelected;

  public ThumbnailImages(String image, boolean isSelected) {
    this.image = image;
    this.isSelected = isSelected;
  }

  public ThumbnailImages(int drawable, boolean isSelected) {
    this.drawable = drawable;
    this.isSelected = isSelected;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getDrawable() {
    return drawable;
  }

  public void setDrawable(int drawable) {
    this.drawable = drawable;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }
}
