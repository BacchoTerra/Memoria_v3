package com.bacchoterra.memoriav3.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeUtil {

    public void addSwipeToRecyclerView(OnSwipedListener listener,RecyclerView target) {

        ItemTouchHelper.Callback callback= new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int DRAG_FLAGS = ItemTouchHelper.ACTION_STATE_IDLE;
                int SWIPE_FLAGS = ItemTouchHelper.END;


                return makeMovementFlags(DRAG_FLAGS,SWIPE_FLAGS);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                listener.onSwiped(viewHolder.getLayoutPosition());

            }
        };

        new ItemTouchHelper(callback).attachToRecyclerView(target);

    }


    public interface OnSwipedListener{

        void onSwiped(int position);

    }

}
