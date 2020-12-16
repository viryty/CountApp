package com.viryty.countapp.utils;

import android.content.Context;
import android.icu.text.MessagePattern;

import com.viryty.countapp.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubjectItems {

    private static SubjectItems subjectItems;
    private Context context;

    private ArrayList<Subject> subjects;
    private ArrayList<Subject> subjectsFavorite;


    public static SubjectItems get(Context context) {
        if(subjectItems == null) {
            subjectItems = new SubjectItems(context);
        }
        return subjectItems;
    }

    public SubjectItems(Context context) {
        subjects = new ArrayList<>();
        subjectsFavorite = new ArrayList<>();
        this.context = context;

        Subject subject1 = new Subject();
        subject1.setTitle("1");
        subject1.setFavorite(true);

        Subject subject2 = new Subject();
        subject2.setTitle("2");

        addSubject(subject1);
        addSubject(subject2);
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        updateSubjectsFavorite();
    }

    public void addSubject(int position, Subject subject) {
        subjects.add(position, subject);
        updateSubjectsFavorite();
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public Subject getSubject(UUID id) {
        Subject subject = new Subject();
        for(int i = 0; i < subjects.size(); i++) {
            if(subjects.get(i).getId() == id) {
                subject = subjects.get(i);
            }
        }
        return subject;
    }

    public Subject getSubject(int position) {
        Subject subject = new Subject();
        for(int i = 0; i < subjects.size(); i++) {
            if(i == position) {
                subject = subjects.get(i);
            }
        }
        return subject;
    }

    public void updateSubject(Subject subject) {
        for(int i =  0; i < subjects.size(); i++) {
            if(subjects.get(i).getId() == subject.getId()) {
                subjects.remove(i);
                subjects.add(i, subject);
            }
        }
        updateSubjectsFavorite();
    }

    public void updateSubjectMove(int fromPosition, int toPosition) {
        Subject subject1 = subjects.remove(fromPosition);
        subjects.add(toPosition > fromPosition + 1 ? toPosition - 1 : toPosition, subject1);
        updateSubjectsFavorite();
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
        updateSubjectsFavorite();
    }

    public void removeSubject(int position) {
        subjects.remove(position);
        updateSubjectsFavorite();
    }

    public Subject getSubjectFavorite(int position) {
        Subject subject = new Subject();
        for(int i = 0; i < subjectsFavorite.size(); i++) {
            if(i == position) {
                subject = subjectsFavorite.get(i);
            }
        }
        return subject;
    }

    public void updateSubjectsFavorite() {
        subjectsFavorite.clear();
        for(int i = 0; i < subjects.size(); i++) {
            if(subjects.get(i).isFavorite()) {
                subjectsFavorite.add(subjects.get(i));
            }
        }
    }

    public void updateSubjectFavorite(Subject subject) {
        for(int i =  0; i < subjectsFavorite.size(); i++) {
            if(subjectsFavorite.get(i).getId() == subject.getId()) {
                subjectsFavorite.remove(i);
            }
        }
    }

    public ArrayList<Subject> getSubjectsFavorite() {
        subjectsFavorite.clear();
        for(int i = 0; i < subjects.size(); i++) {
            if(subjects.get(i).isFavorite()) {
                subjectsFavorite.add(subjects.get(i));
            }
        }
        return subjectsFavorite;
    }
}
