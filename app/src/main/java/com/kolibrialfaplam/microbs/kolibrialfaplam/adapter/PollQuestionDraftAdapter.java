package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PollQuestion;

import java.util.ArrayList;
import java.util.List;

public class PollQuestionDraftAdapter  extends BaseExpandableListAdapter {

    Context context;
    private ArrayList<String> groupTitleList;
    private List<PollQuestion> questionList;
    private final int GROUP_TYPE_1 = 0;

    public PollQuestionDraftAdapter(Context context, ArrayList<String> groupTitleList, List<PollQuestion> questionList) {
        this.context = context;
        this.groupTitleList = groupTitleList;
        this.questionList = questionList;
    }

    @Override
    public int getGroupCount() {
        return groupTitleList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return questionList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupTitleList.get(groupPosition);
    }

    @Override
    public Object  getChild(int groupPosition, int childPosition) {
        return questionList.get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final String titleGroup = (String) getGroup(groupPosition);

        int groupType = getGroupType(groupPosition);
        if(convertView == null || convertView.getTag() != (Object)groupType){
            if(groupType == GROUP_TYPE_1){
                convertView = inflater.inflate(R.layout.questions_group_header, null);

            }
        }else{

        }
        TextView title = convertView.findViewById(R.id.headerTitleTv);
        ImageView arrowIndicator = convertView.findViewById(R.id.expandableListArrowIndicator);
        if(isExpanded){
            arrowIndicator.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
        }else{
            arrowIndicator.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
        }

        title.setText(titleGroup);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PollQuestion child = (PollQuestion) getChild(groupPosition, childPosition);

        if(convertView == null){
          convertView = LayoutInflater.from(context).inflate(R.layout.item_question_draft, null);
        }

        TextView draftQuestionTv = convertView.findViewById(R.id.draftQuestionTv);
        TextView draftAnswerTv = convertView.findViewById(R.id.draftAnswerTv);
        draftQuestionTv.setText(child.getQuestionText());
        draftAnswerTv.setText(child.getPollQuestionAnswer());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
