package com.example.courseregistration;

import com.example.courseregistration.Activity.SignUpActivity;
import com.example.courseregistration.Activity.StudentActivity.EnrollDialog;
import com.example.courseregistration.Activity.StudentActivity.UnenrollDialog;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class StudentTest {
    @Test
    public void testEnrolledSuccessfullyTrue(){
        assertTrue(EnrollDialog.enrolledSuccessfully("MAT1111", "Student1"));}


    @Test
    public void testEnrolledSuccessfullyFalse(){
        assertFalse(EnrollDialog.notEnrolledSuccessfully("MAT1111", "Student1"));}

    @Test
    public void testUnenrolledTrue(){
        assertTrue(UnenrollDialog.unenrolledTrue("MAT1111", "Student1"));}


    @Test
    public void testUnenrolledFalse(){
        assertFalse(UnenrollDialog.unenrolledFalse("MAT1111", "Student1"));}

    @Test
    public void testFullCapacityFalse(){
        assertFalse(EnrollDialog.fullCapacity("MAT1111"));}

}
