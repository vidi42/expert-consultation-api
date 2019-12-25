package com.code4ro.legalconsultation.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The convention is that:<br>
 * <ul>
 * <li>An explicit <b>true</b> vote up will mean a vote up.
 * <li>An explicit <b>false</b> vote up will mean a vote down.
 * <li>An explicit <b>null</b> vote up will mean a vote reset (either delete or
 * nullify all the votes so far - this is to be decided).
 * </ul>
 * TODO: clarify the policy for vote resetting...
 */
@Getter
@Setter
public class VoteDto {
  private Boolean voteUp;
}
