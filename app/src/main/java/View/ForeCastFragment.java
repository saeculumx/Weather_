package View;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.weather_.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import Model.ForeCastAdapter;
import Model.ForeCastWeather;
import ViewModel.SharedResource;

public class ForeCastFragment extends Fragment implements OnChartValueSelectedListener, ForeCastAdapter.OnListItemClickListener {
    private final LineChart[] charts = new LineChart[2];
    SharedResource sharedResource;
    ForeCastAdapter foreCastAdapter;
    HomePageFragment homePageFragment = new HomePageFragment();
    View view;
    TextView tvX,tvY;
    LineChart chart;
    ArrayList<ForeCastWeather> foreCastWeathers = new ArrayList<>();
    LineData data;
    LineData data1;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_fore_cast, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutFor);
        sharedResource = new SharedResource();
        EventBus.getDefault().register(this);
        System.out.println("f1f" +sharedResource.getForeCastWeatherArrayList().size());
        foreCastWeathers = sharedResource.getForeCastWeatherArrayList();
        nestedScrollView = view.findViewById(R.id.NestedScroll);
        recyclerView = view.findViewById(R.id.weatherRecycleView2);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        foreCastAdapter = new ForeCastAdapter(foreCastWeathers, this);
        recyclerView.setAdapter(foreCastAdapter);
        //
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().setTitle("LineChartActivityColored");
        Description description = new Description();
        description.setText("Max Temperature");
        description.setTextColor(Color.WHITE);
        description.setTextSize(15);
        Description description2 = new Description();
        description2.setText("Min Temperature");
        description2.setTextColor(Color.WHITE);
        description2.setTextSize(15);
        charts[0] = view.findViewById(R.id.chart1);
        charts[1] = view.findViewById(R.id.chart2);
        charts[0].setDescription(description);
        charts[1].setDescription(description2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data = getData(8, foreCastWeathers, true);
                setupChart(charts[0], data, colors[0 % colors.length]);
                data1 = getData(8, foreCastWeathers, false);
                setupChart(charts[1], data1, colors[1 % colors.length]);
                foreCastAdapter = new ForeCastAdapter(foreCastWeathers, ForeCastFragment.this);
                recyclerView.setAdapter(foreCastAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
    int a = 0;
    @Subscribe
    public void onEvent(ArrayList<ForeCastWeather> weatherArrayList)
    {
        foreCastAdapter = new ForeCastAdapter(foreCastWeathers, ForeCastFragment.this);
        recyclerView.setAdapter(foreCastAdapter);
        this.foreCastWeathers = weatherArrayList;
        System.out.println("Data received in FCF, size: "+weatherArrayList.size()+" Value: "+weatherArrayList.get(0).getCHigh());
        if (data == null&&data1 == null&&a<300) {
            data = getData(8, weatherArrayList, true);
            setupChart(charts[0], data, colors[0 % colors.length]);
            data1 = getData(8, weatherArrayList, false);
            setupChart(charts[1], data1, colors[1 % colors.length]);
            a = 0;
        }
        else {a++;}
        //setData(7,weatherArrayList);
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
    //
    private final int[] colors = new int[] {
            Color.alpha(0x90FFFFFF),
            Color.alpha(0x90FFFFFF)
    };

    private void setupChart(LineChart chart, LineData data, int color) {

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);

        // no description text
        chart.getDescription().setEnabled(true);
        // chart.setDrawHorizontalGrid(false);
        //
        // enable / disable grid background
        chart.setDrawGridBackground(false);
        //chart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        chart.setBackgroundColor(color);

        // set custom chart offsets (automatic offset calculation is hereby disabled)
        chart.setViewPortOffsets(10, 0, 10, 0);

        // add data
        chart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setEnabled(false);

        // animate calls invalidate()...
        chart.animateX(500,Easing.EaseInOutCirc);
    }

    private LineData getData(int count,ArrayList<ForeCastWeather> weatherArrayList,boolean function) {

        ArrayList<Entry> values = new ArrayList<>();
        if (function) {
            for (int i = 0; i < count; i++) {
                float val = (float) (weatherArrayList.get(i).getCHigh()+5);
                values.add(new Entry(i, val));
            }
        }
        else
        {
            for (int i = 0; i < count; i++) {
                float val = (float) (weatherArrayList.get(i).getCLow()+5);
                values.add(new Entry(i, val));
            }
        }
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);
        set1.setLineWidth(1.75f);
        set1.setCircleRadius(1.1f);
        set1.setCircleHoleRadius(0.5f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);
        // create a data object with the data sets
        return new LineData(set1);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        int serNum = clickedItemIndex;
    }
}