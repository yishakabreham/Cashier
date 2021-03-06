package et.com.cashier.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import et.com.cashier.R;
import et.com.cashier.activities.windowPassengerDetail;
import et.com.cashier.buffer.TicketInfo;
import et.com.cashier.network.retrofit.pojo.SeatArrangement;
import et.com.cashier.network.retrofit.pojo.SubTrip;
import et.com.cashier.utilities.CommonElements;

public class SeatTicket extends RecyclerView.Adapter<SeatTicket.TicketViewHolder>
{
    private Context context;
    private ArrayList<TicketInfo> list;
    private double uPrices;
    private OnTicketListener onSeatListener;

    public static class TicketViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageValidated, imageIsChild;
        TextView seatName, txtRouteDescEn, txtRouteDescAm, txtUnitPrice, txtPassengerName, txtPassengerPhoneNumber;

        public TicketViewHolder(View v) {
            super(v);

            imageValidated = v.findViewById(R.id.imgValidated);
            imageIsChild = v.findViewById(R.id.imgChild);
            seatName = v.findViewById(R.id.txtSeatName);
            txtRouteDescEn = v.findViewById(R.id.txtSRouteDesc);
            txtRouteDescAm = v.findViewById(R.id.txtSRouteDescTranslation);
            txtUnitPrice = v.findViewById(R.id.txtSTripUnitAmount);
            txtPassengerName = v.findViewById(R.id.txtSPassengerName);
            txtPassengerPhoneNumber = v.findViewById(R.id.txtSPassengerPhone);
        }
    }

    public SeatTicket(ArrayList<TicketInfo> list, Context context, double uPrice) {
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

        switch (list.get(position).getFlag())
        {
            case 0:
                holder.imageValidated.setImageDrawable(null);
                holder.txtPassengerName.setText("");
                holder.txtPassengerPhoneNumber.setText("");
                break;
            case -1:
                holder.imageValidated.setImageResource(R.drawable.icon_invalid);
                holder.txtPassengerName.setText("");
                holder.txtPassengerPhoneNumber.setText("");
                break;
            case 1:
                holder.imageValidated.setImageResource(R.drawable.icon_check);
                holder.txtPassengerName.setText(list.get(position).getPassenger().getFirstName() + " " + list.get(position).getPassenger().getMiddleName());
                holder.txtPassengerPhoneNumber.setText(list.get(position).getPassenger().getPhoneNumber());
                break;
        }

        holder.seatName.setText(list.get(position).getSeatName());
        holder.txtRouteDescEn.setText(list.get(position).getSource() + " - " + list.get(position).getDestination());
        holder.txtRouteDescAm.setText(list.get(position).getSourceLocal() + " - " + list.get(position).getDestinationLocal());
        holder.txtUnitPrice.setText(list.get(position).getDiscount().equals(0.0) ? "ETB " + CommonElements.currencyFormat(String.valueOf(list.get(position).getPrice())) :
                "ETB " + CommonElements.currencyFormat(String.valueOf(list.get(position).getPrice())));
        holder.imageIsChild.setVisibility(list.get(position).getPassenger() != null && list.get(position).getPassenger().getChild() == 1 ? View.VISIBLE : View.INVISIBLE);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface OnTicketListener
    {
        void onTicketClick(int position);
    }
}
