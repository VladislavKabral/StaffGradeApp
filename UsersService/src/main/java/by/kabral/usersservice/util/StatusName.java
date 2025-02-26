package by.kabral.usersservice.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class StatusName {

  public final String AT_WORK = "AT_WORK";
  public final String ON_VACATION = "ON_VACATION";
  public final String FIRED = "FIRED";
  public final String NOT_FIRED = "NOT_FIRED";

  public final List<String> STATUS_NAMES = List.of(AT_WORK,ON_VACATION,FIRED);
}
