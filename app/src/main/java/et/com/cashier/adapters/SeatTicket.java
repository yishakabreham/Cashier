package et.com.cashier.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import et.com.cashier.R;
import et.com.cashier.network.retrofit.pojo.SeatArrangement;

public class SeatTicket extends RecyclerView.Adapter<SeatTicket.TicketViewHolder>
{
    private Context context;
    private ArrayList<SeatArrangement> list;
    private double uPrices;

    public static class TicketViewHolder extends RecyclerView.ViewHolder
    {
        TextView seatName, uPrice;

        public TicketViewHolder(View v) {
            super(v);

            seatName = v.findViewById(R.id.txtSeatName);
            uPrice = v.findViewById(R.id.txtUPrice);
        }
    }

    public SeatTicket(ArrayList<SeatArrangement> list, Context context, double uPrice) {
        this.list = list;
        this.uPrices = uPrice;
        this.context = context;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TicketViewHolder holder, final int position) {

        holder.seatName.setText(list.get(position).getName());
        holder.uPrice.setText("ETB " + String.format("%.2f", uPrices));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
