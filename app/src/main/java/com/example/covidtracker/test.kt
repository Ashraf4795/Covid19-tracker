package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.core.ui.markersfactory.Marker
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.ColoredFill
import com.anychart.graphics.vector.Stroke
import com.example.covidtracker.core.models.GlobalData
import com.example.covidtracker.global.GlobalFragment
import kotlinx.android.synthetic.main.statistics_card.*

class test : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

    }

    private fun setGlobalStatistics(globalData: GlobalData) {
        val cartesian = AnyChart.line()

        cartesian.animation(true)


        cartesian.crosshair()
            .yLabel(true) // TODO ystroke
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)



        val seriesData: MutableList<DataEntry> = ArrayList()
        seriesData.add(CustomDataEntry("1986", 3.6))
        seriesData.add(CustomDataEntry("1987", 7.1))
        seriesData.add(CustomDataEntry("1988", 8.5))
        seriesData.add(CustomDataEntry("1989", 9.2))
        seriesData.add(CustomDataEntry("1990", 10.1))
        seriesData.add(CustomDataEntry("1991", 11.6))
        seriesData.add(CustomDataEntry("1992", 16.4))
        seriesData.add(CustomDataEntry("1986", 3.6))
        seriesData.add(CustomDataEntry("1987", 7.1))
        seriesData.add(CustomDataEntry("1988", 8.5))
        seriesData.add(CustomDataEntry("1989", 9.2))
        seriesData.add(CustomDataEntry("1990", 10.1))
        seriesData.add(CustomDataEntry("1991", 11.6))
        seriesData.add(CustomDataEntry("1992", 16.4))
        seriesData.add(CustomDataEntry("1986", 3.6))
        seriesData.add(CustomDataEntry("1987", 7.1))
        seriesData.add(CustomDataEntry("1988", 8.5))
        seriesData.add(CustomDataEntry("1989", 9.2))
        seriesData.add(CustomDataEntry("1990", 10.1))
        seriesData.add(CustomDataEntry("1991", 11.6))
        seriesData.add(CustomDataEntry("1992", 16.4))

        val seriesData2: MutableList<DataEntry> = ArrayList()
        seriesData2.add(CustomDataEntry("1986", 4.6))
        seriesData2.add(CustomDataEntry("1987", 7.1))
        seriesData2.add(CustomDataEntry("1988", 20.5))
        seriesData2.add(CustomDataEntry("1989", 9.2))
        seriesData2.add(CustomDataEntry("1990", 10.1))
        seriesData2.add(CustomDataEntry("1991", 11.6))
        seriesData2.add(CustomDataEntry("1992", 16.4))
        seriesData.add(CustomDataEntry("1986", 3.6))
        seriesData.add(CustomDataEntry("1987", 7.1))
        seriesData.add(CustomDataEntry("1988", 8.5))
        seriesData.add(CustomDataEntry("1989", 9.2))
        seriesData.add(CustomDataEntry("1990", 10.1))
        seriesData.add(CustomDataEntry("1991", 11.6))
        seriesData.add(CustomDataEntry("1992", 16.4))
        seriesData.add(CustomDataEntry("1986", 3.6))
        seriesData.add(CustomDataEntry("1987", 7.1))
        seriesData.add(CustomDataEntry("1988", 8.5))
        seriesData.add(CustomDataEntry("1989", 9.2))
        seriesData.add(CustomDataEntry("1990", 10.1))
        seriesData.add(CustomDataEntry("1991", 11.6))
        seriesData.add(CustomDataEntry("1992", 16.4))

        val seriesData3: MutableList<DataEntry> = ArrayList()
        seriesData3.add(CustomDataEntry("1986", 34.6))
        seriesData3.add(CustomDataEntry("1987", 7.1))
        seriesData3.add(CustomDataEntry("1988", 8.5))
        seriesData3.add(CustomDataEntry("1989", 9.2))
        seriesData3.add(CustomDataEntry("1990", 30.1))
        seriesData3.add(CustomDataEntry("1991", 11.6))
        seriesData3.add(CustomDataEntry("1992", 16.4))
        seriesData.add(CustomDataEntry("1986", 3.6))
        seriesData.add(CustomDataEntry("1987", 7.1))
        seriesData.add(CustomDataEntry("1988", 8.5))
        seriesData.add(CustomDataEntry("1989", 9.2))
        seriesData.add(CustomDataEntry("1990", 10.1))
        seriesData.add(CustomDataEntry("1991", 11.6))
        seriesData.add(CustomDataEntry("1992", 16.4))
        seriesData.add(CustomDataEntry("1986", 3.6))
        seriesData.add(CustomDataEntry("1987", 7.1))
        seriesData.add(CustomDataEntry("1988", 8.5))
        seriesData.add(CustomDataEntry("1989", 9.2))
        seriesData.add(CustomDataEntry("1990", 10.1))
        seriesData.add(CustomDataEntry("1991", 11.6))
        seriesData.add(CustomDataEntry("2222", 16.4))

        val set= Set.instantiate()
        set.data(seriesData)
        val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val set2 = Set.instantiate()
        set2.data(seriesData2)
        val series2Mapping: Mapping = set2.mapAs("{ x: 'x', value: 'value2' }")

        val set3 = Set.instantiate()
        set3.data(seriesData3)
        val series3Mapping: Mapping = set3.mapAs("{ x: 'x', value: 'value3' }")

        val series1 = cartesian.line(series1Mapping)
        series1.hovered().markers().enabled(true)
        series1.name("confirmed")
        series1.stroke("#418BCA")
        series1.hovered().markers()
            .type(MarkerType.CROSS)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)


        val series2 = cartesian.line(series2Mapping)
        series2.name("Recovered")
        series2.hovered().markers().enabled(true)
        series2.stroke("#02B686")
        series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series2.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        val series3 = cartesian.line(series3Mapping)
        series3.stroke("#FF4947")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
            .stroke("blue")
        series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        any_chart_view.setChart(cartesian)
    }

    private class CustomDataEntry internal constructor(
        x: String?,
        value: Number?
    ) :
        ValueDataEntry(x, value) {
    }
}
