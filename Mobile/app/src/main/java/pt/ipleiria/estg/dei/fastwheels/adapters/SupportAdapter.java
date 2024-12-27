package pt.ipleiria.estg.dei.fastwheels.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class SupportAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> categories; // Group items (categories)
    private HashMap<String, List<String>> faqs; // Child items (answers)

    public SupportAdapter(Context context, List<String> categories, HashMap<String, List<String>> faqs) {
        this.context = context;
        this.categories = categories;
        this.faqs = faqs;
    }

    @Override
    public int getGroupCount() {
        // Number of categories
        return categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // Number of questions/answers for the given category
        return faqs.get(categories.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // Return the category
        return categories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // Return the specific question and answer for the given category
        return faqs.get(categories.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String category = (String) getGroup(groupPosition); // Category name
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, null);
        }
        TextView categoryText = convertView.findViewById(android.R.id.text1);
        categoryText.setText(category);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String answer = (String) getChild(groupPosition, childPosition); // Question and answer
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_2, null);
        }
        TextView answerText = convertView.findViewById(android.R.id.text1);
        answerText.setText(answer);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
