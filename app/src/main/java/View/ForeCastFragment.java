package View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather_.R;
import com.github.mikephil.charting.charts.LineChart;

public class ForeCastFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_fore_cast, container, false);
        LineChart chart = (LineChart) view.findViewById(R.id.LineChar);

        return view;
    }
}