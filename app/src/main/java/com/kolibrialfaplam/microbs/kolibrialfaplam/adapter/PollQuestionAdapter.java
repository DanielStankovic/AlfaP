package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.PollQuestion;

import java.util.ArrayList;

public class PollQuestionAdapter extends BaseExpandableListAdapter {

    Context context;
    private ArrayList<String> groupTitleList;
    private ArrayList<PollQuestion> questionList;
    private static final int GROUP_TYPE_1 = 0;

    private static final int CHILD_TYPE_1 = 0;
    private static final int CHILD_TYPE_2 = 1;


    public PollQuestionAdapter(Context context, ArrayList<String> groupTitleList, ArrayList<PollQuestion> questionList) {
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
        final PollQuestion child = (PollQuestion) getChild(groupPosition, childPosition);
        LayoutInflater inflater = LayoutInflater.from(context);
        int childType = getChildType(groupPosition, childPosition);
        if(convertView == null || convertView.getTag() != (Object)childType){
            switch (childType){
                case CHILD_TYPE_1:
                    convertView = inflater.inflate(R.layout.questions_yes_no_item, null);
                    convertView.setTag(childType);

                    break;
                case CHILD_TYPE_2:
                    convertView = inflater.inflate(R.layout.questions_free_answer_item, null);
                    convertView.setTag(childType);
                    break;
                    default:
                        break;

            }




        }


        switch (childType){
            case CHILD_TYPE_1:
                TextView yesNoQuestionTv = convertView.findViewById(R.id.yesNoQuestionTv);
                RadioGroup yesNoRadioGrp = convertView.findViewById(R.id.yesNoRadioGrp);
                yesNoQuestionTv.setText(child.getQuestionText());
                yesNoRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        child.setPollQuestionAnswer(checkedId == R.id.pollQuestionYesRbtn ? "DA":"NE");
                    }
                });


                break;

            case CHILD_TYPE_2:
                TextView freeAnswerQuestionTv = convertView.findViewById(R.id.freeAnswerQuestionTv);
                TextInputEditText freeAnswerEt = convertView.findViewById(R.id.freeAnswerEt);
                freeAnswerQuestionTv.setText(child.getQuestionText());

                freeAnswerEt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    child.setPollQuestionAnswer(s.toString());
                    }
                });


                break;

            default:
                break;
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //Ovo vraca koliko tipova pitanja postoje. Za sada samo 2 (DA-NE, i SLOBODAN UNOS)
    @Override
    public int getChildTypeCount() {
        return 2;
    }

    //Nisam siguran da ovo treba, ali neka ga. Vraca koliko grupe postoje, za sada samo jedna.
    @Override
    public int getGroupTypeCount() {
        return 1;
    }

    @Override
    public int getGroupType(int groupPosition) {
        return GROUP_TYPE_1;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {

        if(groupPosition == 0){
            if (questionList.get(childPosition).getPollQuestionType() == PollQuestion.QuestionType.YESNO) {
                return CHILD_TYPE_1;
            }else {
                return CHILD_TYPE_2;
            }
        }else{
            return  CHILD_TYPE_1;
        }
    }
}
