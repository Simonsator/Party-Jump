package de.simonsator.partyandfriends.extensions.partyjump.command;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.api.party.abstractcommands.PartySubCommand;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.config.Configuration;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 16.04.17
 */
public class PartyJump extends PartySubCommand {
	private final Configuration CONFIG;

	public PartyJump(List<String> pCommands, int pPriority, String pHelpText, String pPermission, Configuration pConfig) {
		super(pCommands, pPriority, pHelpText, pPermission);
		CONFIG = pConfig;
	}

	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT == pPermissionHeight;
	}

	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = PartyManager.getInstance().getParty(pPlayer);
		if (!isInParty(pPlayer, party))
			return;
		if (party.isLeader(pPlayer)) {
			sendError(pPlayer, new TextComponent(PREFIX + CONFIG.getString("Messages.Party.Jump.Leader")));
			return;
		}
		ServerInfo leaderServer = party.getLeader().getServer();
		if (leaderServer.equals(pPlayer.getServer())) {
			sendError(pPlayer, new TextComponent(PREFIX + CONFIG.getString("Messages.Party.Jump.AlreadyOnServer")));
			return;
		}
		pPlayer.connect(leaderServer);
		pPlayer.sendMessage(PREFIX + CONFIG.getString("Messages.Party.Jump.JumpedTo"));
	}
}
