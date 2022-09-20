package com.example.po;

public class MemoryCard {
    private int imageId;
    private boolean isFaceUp;
    private boolean isMatched;

    public MemoryCard(int imageId, boolean isFaceUp, boolean isMatched) {
        this.imageId = imageId;
        this.isFaceUp = isFaceUp;
        this.isMatched = isMatched;
    }

    // 이미지ID
    public int getImageId()
    {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    // 카드가 앞면, 뒷면
    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    // 정답인가 아닌가
    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched){
        isMatched = matched;
    }
}

