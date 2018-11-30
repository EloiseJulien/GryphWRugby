package ayoolamakinde.eloisejulien.gryphswrugby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ayoolamakinde.eloisejulien.gryphswrugby.models.Event;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Context context, List<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_event,parent, false);
        }

        ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ContactViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.row_event_name);
            viewHolder.location = (TextView) convertView.findViewById(R.id.row_event_location);
            viewHolder.time = (TextView) convertView.findViewById(R.id.row_event_time);

            convertView.setTag(viewHolder);
        }

        Event event = getItem(position);


        viewHolder.name.setText(event.getName());
        viewHolder.location.setText(event.getLocation());
        viewHolder.time.setText(event.getStartHour() + " - " + event.getEndHour());

        return convertView;
    }

    private class ContactViewHolder{
        public TextView name;
        public TextView location;
        public TextView time;

    }
}
