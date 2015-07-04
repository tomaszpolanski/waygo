package com.waygo.view;

import com.waygo.R;
import com.waygo.pojo.flightstatus.Flight;
import com.waygo.utils.RxBinderUtil;
import com.waygo.viewmodels.RepositoryViewModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import rx.android.internal.Preconditions;

public class RepositoryView extends FrameLayout {

    private final RxBinderUtil rxBinderUtil = new RxBinderUtil(this);

    private TextView titleTextView;

    private TextView stargazersTextView;

    private TextView forksTextView;

    public RepositoryView(Context context) {
        super(context);
    }

    public RepositoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleTextView = (TextView) findViewById(R.id.widget_layout_title);
        stargazersTextView = (TextView) findViewById(R.id.widget_layout_stargazers);
        forksTextView = (TextView) findViewById(R.id.widget_layout_forks);
    }

    public void setViewModel(@Nullable RepositoryViewModel viewModel) {
        rxBinderUtil.clear();
        if (viewModel != null) {
            rxBinderUtil.bindProperty(viewModel.getFlight(), this::setFlight);
        }
    }

    private void setFlight(@NonNull Flight flight) {
        Preconditions.checkNotNull(flight, "Flight cannot be null.");

        titleTextView.setText("Name & Status: "
                              + getFlightName(flight) + " "
                              + getArrivalDefinition(flight));
        stargazersTextView.setText("Scheduled departure: " + getDepartureText(flight));
        forksTextView.setText("Scheduled arrival: " + getArrivalText(flight));
    }

    @NonNull
    private String getArrivalDefinition(@NonNull Flight flight) {
        return flight.getArrival().getTimeStatus().getDefinition();
    }

    @NonNull
    private String getFlightName(@NonNull Flight flight) {
        return flight.getMarketingCarrier().getAirlineID()
               + flight.getMarketingCarrier().getFlightNumber();
    }

    @NonNull
    private String getArrivalText(@NonNull Flight flight) {
        return flight.getArrival() != null
                ? flight.getArrival().getScheduledTimeLocal().getDateTime()
                : "Not yet arrived";
    }

    @NonNull
    private String getDepartureText(@NonNull Flight flight) {
        return flight.getDeparture() != null
                ? flight.getDeparture().getScheduledTimeLocal().getDateTime()
                : "Not departed";
    }

}
