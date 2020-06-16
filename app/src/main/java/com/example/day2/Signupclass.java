package com.example.day2;

import java.util.ArrayList;

public class Signupclass {
    static class Person {
        int score;
        String email,name,password;
        Person(String email,String name,String password){
            this.email = email;
            this.name = name;
            this.password = password;
        }
    }
    public ArrayList<Person> heap=new ArrayList<>();
    public void insert(Person node){
        heap.add(node);
        correction_in_heap(heap.size()-1);
    }
    private void correction_in_heap(int value){
        int parent=(value-1)/2;
        if(heap.get(parent).score<heap.get(value).score){
            swap(parent,value);
            correction_in_heap(parent);
        }
        if(heap.get(parent).score==heap.get(value).score){
            if(heap.get(parent).email.charAt(0)<heap.get(value).email.charAt(0)) {
                swap(parent, value);
                correction_in_heap(parent);
            }
        }
    }
    private void swap(int val1, int val2){
        Person temp1=heap.get(val1);
        Person temp2=heap.get(val2);
        heap.set(val1,temp2);
        heap.set(val2,temp1);
    }
    public Person delete(){
        swap(0,this.heap.size()-1);
        Person removed=this.heap.remove(this.heap.size()-1);
        downcorrection(0);
        return removed;
    }
    private void downcorrection(int val) {
        int left=2*val+1;
        int right=2*val+2;
        int pi=val;
        if(left<heap.size() &&heap.get(left).score>heap.get(pi).score){
            pi=left;
        }
        if(right<heap.size() &&heap.get(right).score>heap.get(pi).score){
            pi=right;
        }
        if(right<heap.size() &&heap.get(right).score == heap.get(pi).score){
            if(heap.get(right).email.charAt(0) > heap.get(pi).email.charAt(0)){
                pi=right;
            }
        }
        if(left<heap.size() &&heap.get(left).score == heap.get(pi).score){
            if(heap.get(left).email.charAt(0) > heap.get(pi).email.charAt(0)){
                pi=left;
            }
        }
        if(pi!=val){
            swap(pi,val);
            downcorrection(pi);
        }
    }
}
