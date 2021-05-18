package Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ForeCastAdapter extends RecyclerView.Adapter<ForeCastAdapter.ViewHolder> {
    //SimpleDateFormat format = new SimpleDateFormat("yyyy / MM / dd");
    //Date curDate = new Date(System.currentTimeMillis());
    Calendar calendar = Calendar.getInstance();
    int year,month,day;
    private ArrayList<ForeCastWeather> foreCastWeatherArrayList ;
    private ArrayList<ForeCastAdapter.ViewHolder> viewHolderList;
    final private OnListItemClickListener onListItemClickListener;
    public ForeCastAdapter(ArrayList<ForeCastWeather> foreCastWeatherArrayList,OnListItemClickListener listItemClickListener)
    {
        onListItemClickListener = listItemClickListener;
        viewHolderList = new ArrayList<>();
        if (foreCastWeatherArrayList==null)
        {
            this.foreCastWeatherArrayList = new ArrayList<>();
        }
        else this.foreCastWeatherArrayList = foreCastWeatherArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.forecastviewitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolderList.add(viewHolder);
        return new ViewHolder(view);
    }
    public void getTime()
    {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public void onBindViewHolder(@NonNull ForeCastAdapter.ViewHolder viewHolder, int position) {
        getTime();
        int fDay;
        fDay = (day+position+1);
        if (fDay<28&&month==2) {
            viewHolder.date.setText(year + "/" + month + "/" + (day + position + 1));
        }
        if (fDay>28&&month==2)
        {
            month++;
            day = (fDay-28);
            viewHolder.date.setText(year + "/" + month + "/" + day);
        }
        //4/6/9/11
        if(fDay<30&&(month==4||month==5||month==9||month==11))
        {
            viewHolder.date.setText(year + "/" + month + "/" + (day + position + 1));
        }
        if (fDay>30&&(month==4||month==5||month==9||month==11))
        {
            month++;
            day = (fDay-30);
            viewHolder.date.setText(year + "/" + month + "/" + day);
        }
        if(fDay<31&&(month==1||month==3||month==6||month==7||month==8||month == 10||month==12))
        {
            viewHolder.date.setText(year + "/" + month + "/" + (day + position + 1));
        }
        if(fDay>31&&(month==1||month==3||month==6||month==7||month==8||month == 10||month==12))
        {
            if (month!=12)
            {
                month++;
            }
            else
            {
                month = 1;
            }
            day = (fDay-31);
            viewHolder.date.setText(year + "/" + month + "/" + day);
        }
        //
        Double max = BigDecimal.valueOf(foreCastWeatherArrayList.get(position).getCHigh()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Double min = BigDecimal.valueOf(foreCastWeatherArrayList.get(position).getCLow()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        viewHolder.maxValue.setText(String.valueOf(max));
        viewHolder.minValue.setText(String.valueOf(min));
    }

    @Override
    public int getItemCount() {
        return foreCastWeatherArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView date;
        private final TextView maxValue;
        private final TextView minValue;
        private final View view;
        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            date = itemView.findViewById(R.id.dateTextView);
            maxValue = itemView.findViewById(R.id.maxTableTextView);
            minValue = itemView.findViewById(R.id.minTableTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
    public interface OnListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }
}
