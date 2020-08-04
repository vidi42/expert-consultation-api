package com.code4ro.legalconsultation.invitation.controller;

import com.code4ro.legalconsultation.invitation.mapper.InvitationMapper;
import com.code4ro.legalconsultation.invitation.model.dto.InvitationDto;
import com.code4ro.legalconsultation.invitation.model.persistence.Invitation;
import com.code4ro.legalconsultation.invitation.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;
    private final InvitationMapper invitationMapper;

    @Autowired
    public InvitationController(final InvitationService invitationService,
                                final InvitationMapper invitationMapper) {
        this.invitationService = invitationService;
        this.invitationMapper = invitationMapper;
    }

    @GetMapping("/{code}")
    public ResponseEntity<InvitationDto> getInvitation(@PathVariable("code") String code) {
        final Invitation invitation = invitationService.getInvitation(code);
        return ResponseEntity.ok(invitationMapper.map(invitation));
    }
}
