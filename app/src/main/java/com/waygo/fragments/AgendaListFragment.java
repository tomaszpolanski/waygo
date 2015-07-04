package com.waygo.fragments;

import com.waygo.R;
import com.waygo.WaygoApplication;
import com.waygo.pojo.flightstatus.Flight;
import com.waygo.utils.Instrumentation;
import com.waygo.utils.RxBinderUtil;
import com.waygo.viewmodels.AgendaViewModel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

public class AgendaListFragment extends Fragment {

    private static final String TAG = AgendaListFragment.class.getSimpleName();

    private final RxBinderUtil rxBinderUtil = new RxBinderUtil(this);

    private SimpleStringRecyclerViewAdapter adapter;

    @Inject
    Instrumentation instrumentation;

    @Inject
    AgendaViewModel mAgendaViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WaygoApplication.getInstance().getGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.agenda_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAgendaViewModel.subscribeToDataStore();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new SimpleStringRecyclerViewAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setViewModel(mAgendaViewModel);
    }

    @Override
    public void onPause() {
        super.onPause();
        setViewModel(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAgendaViewModel.unsubscribeFromDataStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAgendaViewModel.dispose();
        mAgendaViewModel = null;
        instrumentation.getLeakTracing().traceLeakage(this);
    }

    public void setViewModel(@Nullable AgendaViewModel viewModel) {
        rxBinderUtil.clear();
        if (viewModel != null) {
            rxBinderUtil.bindProperty(viewModel.getFlight(), this::setFlight);
        }
    }

    private void setFlight(Flight flight) {
        FlightDetails flightDetails = new FlightDetails(toHour(getDepartureTime(flight)),
                                                        toHour(getArrivalTime(flight)),
                                                        getDepartureGate(flight),
                                                        getArrivalGate(flight));
        adapter.setFlightDetails(flightDetails);
        adapter.notifyItemChanged(ViewType.FLIGHT.ordinal());
    }

    private String toHour(String time) {
        try {
            return new SimpleDateFormat("HH:mm")
                    .format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(time));
        } catch (ParseException e) {
            return "?";
        }
    }

    private static String getArrivalTime(Flight flight) {
        if (flight != null && flight.getArrival() != null
            && flight.getArrival().getScheduledTimeLocal() != null
            && flight.getArrival().getScheduledTimeLocal().getDateTime() != null) {
            return flight.getArrival().getScheduledTimeLocal().getDateTime();
        }
        return "?";
    }

    private static String getDepartureTime(Flight flight) {
        if (flight != null && flight.getArrival() != null
            && flight.getDeparture().getScheduledTimeLocal() != null
            && flight.getDeparture().getScheduledTimeLocal().getDateTime() != null) {
            return flight.getDeparture().getScheduledTimeLocal().getDateTime();
        }
        return "?";
    }

    private static String getArrivalGate(Flight flight) {
        if (flight != null && flight.getArrival() != null
            && flight.getArrival().getTerminal() != null
            && flight.getArrival().getTerminal().getGate() != null) {
            return flight.getArrival().getTerminal().getGate();
        }
        return "?";
    }

    private static String getDepartureGate(Flight flight) {
        if (flight != null && flight.getDeparture() != null
            && flight.getDeparture().getTerminal() != null
            && flight.getDeparture().getTerminal().getGate() != null) {
            return flight.getDeparture().getTerminal().getGate();
        }
        return "?";
    }

    private static class FlightDetails {
        private final String mDepartureTime;
        private final String mArrivalTime;
        private final String mDepartureGate;
        private final String mArrivalGate;

        private FlightDetails(String departureTime, String arrival, String departureGate,
                              String arrivalGate) {
            mDepartureTime = departureTime;
            mArrivalTime = arrival;
            mDepartureGate = departureGate;
            mArrivalGate = arrivalGate;
        }

        public FlightDetails() {
            this("?", "?", "?", "?");
        }

        public String getDepartureTime() {
            return mDepartureTime;
        }

        public String getArrivalTime() {
            return mArrivalTime;
        }

        public String getDepartureGate() {
            return mDepartureGate;
        }

        public String getArrivalGate() {
            return mArrivalGate;
        }
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();

        private int mBackground;

        private FlightDetails mFlightDetails = new FlightDetails();

        public void setFlightDetails(FlightDetails flightDetails) {
            mFlightDetails = flightDetails;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(getLayout(viewType), parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        private int getLayout(int viewType) {
            return ViewType.values()[viewType].resourceId;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            setupViewHolder(holder, position);
        }

        private void setupViewHolder(ViewHolder holder, int position) {
            switch (ViewType.values()[position]) {
                case FLIGHT: {
                    TextView flightTime = (TextView) holder.mView.findViewById(R.id.flight_time);
                    flightTime.setText(String.format("%1s - %2s",
                                                      mFlightDetails.getDepartureTime(),
                                                      mFlightDetails.getArrivalTime()));

                    TextView contentView = (TextView) holder.mView.findViewById(R.id.flight_status);
                    contentView.setText(String.format("Departing Gate %1s - Landing Gate %2s",
                                                      mFlightDetails.getDepartureGate(),
                                                      mFlightDetails.getArrivalGate()));
                    break;
                }
                case TRANSIT: {
                    holder.mView.setOnClickListener(v -> {
                        View contentView = holder.mView.findViewById(R.id.transit_more_content);
                        contentView.setVisibility(contentView.getVisibility() == View.VISIBLE
                                                          ? View.GONE
                                                          : View.VISIBLE);
                    });
                    break;
                }
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return ViewType.values().length;
        }

        public int getItemViewType(int position) {
            return ViewType.values()[position].ordinal();
        }
    }

    enum ViewType {

        TODAY(R.layout.today_item),
        NEW_GATE(R.layout.new_gate_flight_item),
        SUN(R.layout.sun_item),
        FLIGHT(R.layout.flight_item),
        COLD(R.layout.cold_item),
        TRANSIT(R.layout.transit_item),
        MAP(R.layout.map_item);

        private @IdRes int resourceId;

        ViewType(int resourceId) {
            this.resourceId = resourceId;
        }

    }
}
