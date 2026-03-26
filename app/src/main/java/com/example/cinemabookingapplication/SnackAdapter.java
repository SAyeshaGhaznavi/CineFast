package com.example.cinemabookingapplication;

import android.content.Context;
import android.view.*;
import android.widget.*;

import java.util.List;

public class SnackAdapter extends BaseAdapter {

    private Context context;
    private List<Snack> snackList;

    public SnackAdapter(Context context, List<Snack> snackList) {
        this.context = context;
        this.snackList = snackList;
    }

    @Override
    public int getCount() {
        return snackList.size();
    }

    @Override
    public Object getItem(int position) {
        return snackList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_snack, parent, false);
        }

        Snack snack = snackList.get(position);

        ImageView image = convertView.findViewById(R.id.snackImage);
        TextView name = convertView.findViewById(R.id.snackName);
        TextView price = convertView.findViewById(R.id.snackPrice);
        TextView qty = convertView.findViewById(R.id.snackQty);
        Button plus = convertView.findViewById(R.id.btnPlus);
        Button minus = convertView.findViewById(R.id.btnMinus);

        image.setImageResource(snack.image);
        name.setText(snack.name);
        price.setText("$" + snack.price);
        qty.setText(String.valueOf(snack.quantity));

        plus.setOnClickListener(v -> {
            snack.quantity++;
            qty.setText(String.valueOf(snack.quantity));
        });

        minus.setOnClickListener(v -> {
            if (snack.quantity > 0) {
                snack.quantity--;
                qty.setText(String.valueOf(snack.quantity));
            }
        });

        return convertView;
    }
}