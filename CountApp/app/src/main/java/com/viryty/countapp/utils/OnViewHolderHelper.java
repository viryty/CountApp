package com.viryty.countapp.utils;

public interface OnViewHolderHelper {

    void onMove(int fromPosition, int toPosition);

    void onRemove(int position);

    void onRemoveFavorite(int position);

    void onClick(int position);
}
