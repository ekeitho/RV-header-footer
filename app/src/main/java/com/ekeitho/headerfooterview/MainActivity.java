package com.ekeitho.headerfooterview;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.ekeitho.headerfooterview.Utils.BOTH;
import static com.ekeitho.headerfooterview.Utils.FOOTER;
import static com.ekeitho.headerfooterview.Utils.HEADER;
import static com.ekeitho.headerfooterview.Utils.ITEM;

public class MainActivity extends AppCompatActivity {


    private int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private int spanCount = 2;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initial setup
        View headerView = Utils.createDynamicView(this);
        int viewResource = R.layout.footer_view;

        // setting mode tells us how to set up our logic
        mode = Utils.getSetupMode(headerView, viewResource);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        HeaderFooterAdapter adapter = new HeaderFooterAdapter(array, headerView, viewResource);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);

        // we want the header to span the full width
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mode) {
                    case HEADER:
                        if (position == 0) {
                            return spanCount;
                        }
                        return 1;
                    case FOOTER:
                        if (position == array.length) {
                            return spanCount;
                        }
                        return 1;
                    case BOTH:
                        if (position == 0 || position == array.length + 1) {
                            return spanCount;
                        }
                        return 1;
                    default:
                        return 1;
                }
            }
        });

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int padding = Utils.dpToPx(getResources(), 16);

                if (position == 0 && (mode == HEADER || mode == BOTH)) {
                    outRect.left = padding;
                    outRect.right = padding;
                    outRect.top = padding;
                    outRect.bottom = padding;
                    // this is an optimization to not check the next conditional
                    return;
                }

                if ((mode == FOOTER && position == array.length)
                        || (mode == BOTH && position == array.length + 1)) {
                    outRect.left = padding;
                    outRect.right = padding;
                    outRect.top = padding;
                    outRect.bottom = padding;
                }
                //super.getItemOffsets(outRect, view, parent, state);
                // -MUST- take super call out or results are over written
            }
        });

        //connect the bridge
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
