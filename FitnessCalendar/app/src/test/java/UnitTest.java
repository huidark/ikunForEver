//import org.junit.Test;
//
//import static org.junit.Assert.*;
//import android.os.Bundle;
//
//import com.example.fitnesscalendar.R;
//import com.example.fitnesscalendar.ui.activity.BarChartActivity;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.utils.ColorTemplate;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.mockito.Mockito.verify;
///**
// * Unit test
// *
// */
//public class UnitTest {
//    @Mock
//    private BarChart barChart;
//    @Mock
//    private BarDataSet barDataSet;
//    @Mock
//    private BarData barData;
//
//    private BarChartActivity barChartActivity;
//    private Bundle bundle;
//    private HashMap<String, Double> weightDataMap;
//    @Before
//    public void setUp() throws Exception {
//        // set up the barchart activity
//        barChartActivity = new BarChartActivity();
//        bundle = new Bundle();
//        weightDataMap = new HashMap<>();
//        weightDataMap.put("2023-04-01", 70.5);
//        weightDataMap.put("2023-04-02", 71.0);
//        bundle.putSerializable("weightDataMap", weightDataMap);
//    }
//
//    @Test
//    public void testBarChartActivity_onCreate() {
//        barChartActivity.onCreate(bundle);
//
//        Mockito.verify(barChart).setData(barData);
//        Mockito.verify(barDataSet).setColors(ColorTemplate.COLORFUL_COLORS);
//        Mockito.verify(barDataSet).setValueTextColor(Mockito.anyInt());
//        Mockito.verify(barDataSet).setValueTextSize(Mockito.anyFloat());
//        Mockito.verify(barChart).getDescription().setEnabled(true);
//
//        verify(barChartActivity).setContentView(R.layout.activity_bar_chart);
//
//    }
//
//}