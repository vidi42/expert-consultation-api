package com.code4ro.legalconsultation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.code4ro.legalconsultation.config.security.CurrentUserService;
import com.code4ro.legalconsultation.model.dto.VoteDto;
import com.code4ro.legalconsultation.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.model.persistence.DocumentNode;
import com.code4ro.legalconsultation.model.persistence.UserRole;
import com.code4ro.legalconsultation.model.persistence.Vote;
import com.code4ro.legalconsultation.repository.VoteRepository;
import com.code4ro.legalconsultation.service.api.DocumentNodeService;
import com.code4ro.legalconsultation.service.impl.VoteServiceImpl;
import com.code4ro.legalconsultation.util.DocumentNodeFactory;
import com.code4ro.legalconsultation.util.RandomObjectFiller;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {

  @Mock
  private VoteRepository voteRepository;
  @Mock
  private CurrentUserService currentUserService;
  @Mock
  private DocumentNodeService documentNodeService;
  @InjectMocks
  private VoteServiceImpl voteService;

  @Captor
  private ArgumentCaptor<Vote> voteArgumentCaptor;

  private final DocumentNodeFactory documentNodeFactory = new DocumentNodeFactory();
//  private final CommentFactory commentFactory = new CommentFactory();
  private ApplicationUser currentUser;
  private DocumentNode documentNode;
  private UUID id;
  private Vote alreadyExistingVote;

  @Before
  public void before() {
    currentUser = RandomObjectFiller.createAndFill(ApplicationUser.class);
    currentUser.getUser().setRole(UserRole.ADMIN);
    when(currentUserService.getCurrentUser()).thenReturn(currentUser);
    documentNode = documentNodeFactory.create();
    when(documentNodeService.getEntity(any())).thenReturn(documentNode);
    id = UUID.randomUUID();
    alreadyExistingVote = new Vote();
    alreadyExistingVote.setOwner(currentUser);
    alreadyExistingVote.setDocumentNode(documentNode);
    alreadyExistingVote.setVoteUp(true);
    alreadyExistingVote.setVoteDown(false);

  }

  /**
   * This particular test captures the fact that a vote has not been casted yet.
   */
  @Test
  public void voteUp() {
    final VoteDto upVoteDto = new VoteDto();
    upVoteDto.setVoteUp(true);
    voteService.vote(id, upVoteDto);

    verify(voteRepository).save(voteArgumentCaptor.capture());
    final Vote upVote = voteArgumentCaptor.getValue();
    assertThat(upVote.getOwner()).isEqualTo(currentUser);
    assertThat(upVote.getDocumentNode()).isEqualTo(documentNode);
    assertThat(upVote.getVoteUp()).isEqualTo(true);
    assertThat(upVote.getVoteDown()).isEqualTo(false);
  }

  /**
   * This test is not fundamentally different (as logic) as {@link #voteUp()} but
   * is simulates that the vote already exists...
   */
  @Test
  public void voteDown() {
    final VoteDto downVoteDto = new VoteDto();
    downVoteDto.setVoteUp(false);

    when(voteRepository.findByDocumentNodeIdAndOwnerId(id, currentUser.getId())).thenReturn(alreadyExistingVote);
    voteService.vote(id, downVoteDto);

    verify(voteRepository).save(voteArgumentCaptor.capture());
    final Vote downVote = voteArgumentCaptor.getValue();

    assertThat(downVote.getOwner()).isEqualTo(currentUser);
    assertThat(downVote.getDocumentNode()).isEqualTo(documentNode);
    assertThat(downVote.getVoteUp()).isEqualTo(false);
    assertThat(downVote.getVoteDown()).isEqualTo(true);
  }

  /**
   * This test "resets" a vote... Not clear if we should delete the vote or make
   * nullify the fields in the repository.
   */
  @Test
  public void voteNull() {
    // finally we try to nullify the votes
    final VoteDto nullVoteDto = new VoteDto();
    nullVoteDto.setVoteUp(null);
    when(voteRepository.findByDocumentNodeIdAndOwnerId(id, currentUser.getId())).thenReturn(alreadyExistingVote);
    voteService.vote(id, nullVoteDto);

    verify(voteRepository).save(voteArgumentCaptor.capture());
    final Vote nullVote = voteArgumentCaptor.getValue();

    assertThat(nullVote.getOwner()).isEqualTo(currentUser);
    assertThat(nullVote.getDocumentNode()).isEqualTo(documentNode);
    assertThat(nullVote.getVoteUp()).isEqualTo(null);
    assertThat(nullVote.getVoteDown()).isEqualTo(null);

  }

  /**
   * This test "resets" a vote... Not clear if we should delete the vote or make
   * nullify the fields in the repository.
   */
  @Test
  public void voteNullNoVoteExisting() {
    // finally we try to nullify the votes
    final VoteDto nullVoteDto = new VoteDto();
    nullVoteDto.setVoteUp(null);
    Object result = voteService.vote(id, nullVoteDto);
    assertThat(result).isEqualTo(null);
  }

}
