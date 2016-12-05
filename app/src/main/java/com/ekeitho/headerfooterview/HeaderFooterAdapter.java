package com.ekeitho.headerfooterview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.ekeitho.headerfooterview.Utils.BOTH;
import static com.ekeitho.headerfooterview.Utils.FOOTER;
import static com.ekeitho.headerfooterview.Utils.HEADER;
import static com.ekeitho.headerfooterview.Utils.ITEM;

class HeaderFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int[] list;
    private View headerView;
    private int footerResource;
    private int mode;

    // this is just for demonstration that you can pass
    // either a view or a resource identifier
    HeaderFooterAdapter(int[] list, View headerView, int resource) {
        this.list = list;
        this.headerView = headerView;
        this.footerResource = resource;
        mode = Utils.getSetupMode(headerView, resource);
    }

    private class HeaderFooterView extends RecyclerView.ViewHolder {
        HeaderFooterView(View view) {
            super(view);
        }
    }

    private class ItemView extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;

        ItemView(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.item_view);
            textView = (TextView) view.findViewById(R.id.item_text_view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new HeaderFooterView(this.headerView);
        }
        if (viewType == FOOTER) {
            return new HeaderFooterView(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent, false));
        }
        return new ItemView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        // if there is both footerResource and header
        switch (mode) {
            case BOTH:
                if (position == 0) {
                    return HEADER;
                } else if (position == list.length + 1) {
                    return FOOTER;
                }
                return ITEM;
            case HEADER:
                return position == 0 ? HEADER : ITEM;
            case FOOTER:
                return position == list.length ? FOOTER : ITEM;
            default:
                return ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int actualPosition = position;
        int viewType = holder.getItemViewType();

        switch (viewType) {
            case ITEM:
                if (this.headerView != null) {
                    actualPosition = position - 1;
                }

                // just for visual sake
                String text = "" + list[actualPosition];

                // if there isn't a header than all product alignment is okay,
                // regardless of the footerResource
                ItemView itemView = (ItemView) holder;
                itemView.linearLayout.setBackgroundColor(Color.parseColor(Utils.colorGenerator()));
                itemView.textView.setText(text);
                break;
            case HEADER:
                break;
            case FOOTER:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (mode) {
            case BOTH:
                // if both, return 2 additional for header & footerResource
                return list.length + 2;
            case ITEM:
                // if just items, return just the length
                return list.length;
            default:
                // if either or, return 1 additional
                return list.length + 1;
        }
    }

}