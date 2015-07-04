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

        titleTextView.setText("Name & Status: " + flight.getMarketingCarrier().getAirlineID()
                              + flight.getMarketingCarrier().getFlightNumber() + " "
                              + flight.getFlightStatus().getDefinition());
        stargazersTextView
                .setText("Departure: " + flight.getDeparture().getActualTimeLocal().getDateTime());
        forksTextView.setText(
                "Estimated arrival: " + flight.getArrival().getEstimatedTimeLocal().getDateTime());
    }
}
