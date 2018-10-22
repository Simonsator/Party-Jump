package de.simonsator.partyandfriends.extensions.partyjump;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import de.simonsator.partyandfriends.extensions.partyjump.command.PartyJump;
import de.simonsator.partyandfriends.extensions.partyjump.configuration.JumpConfig;
import de.simonsator.partyandfriends.main.listener.ServerSwitchListener;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;

/**
 * @author Simonsator
 * @version 1.0.0 16.04.17
 */
public class JumpPlugin extends PAFExtension implements Listener {
	private String changedServerMessage;

	@Override
	public void onEnable() {
		try {
			Configuration config = new JumpConfig(new File(getDataFolder(), "config.yml")).getCreatedConfiguration();
			PartyCommand.getInstance().addCommand(new PartyJump(config.getStringList("Commands.Party.Jump.Names"),
					config.getInt("Commands.Party.Jump.Priority"),
					config.getString("Messages.Party.Jump.Help"), config.getString("Commands.Party.Jump.Permission"),
					config));
			if (config.getBoolean("General.DisableAutoJumpOnServerSwitch"))
				getProxy().getPluginManager().unregisterListener(ServerSwitchListener.getInstance());
			if (config.getBoolean("General.InformAboutLeaderServerSwitch")) {
				changedServerMessage = config.getString("Messages.Party.LeaderChangedServer");
				getProxy().getPluginManager().registerListener(this, this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onServerSwitch(ServerSwitchEvent pEvent) {
		OnlinePAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pEvent.getPlayer());
		PlayerParty party = player.getParty();
		if (party != null && party.isLeader(player)) {
			party.sendMessage(PartyCommand.getInstance().getPrefix() + changedServerMessage.replace("[SERVER", pEvent.getPlayer().getServer().getInfo().getName()));
		}
	}

	public void reload() {
		onEnable();
	}
}
