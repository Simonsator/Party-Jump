package de.simonsator.partyandfriends.extensions.partyjump;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.extensions.partyjump.command.PartyJump;
import de.simonsator.partyandfriends.extensions.partyjump.configuration.JumpConfig;
import de.simonsator.partyandfriends.party.command.PartyCommand;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author Simonsator
 * @version 1.0.0 16.04.17
 */
public class JumpPlugin extends PAFExtension {

	@Override
	public void onEnable() {
		try {
			Configuration config = new JumpConfig(new File(getDataFolder(), "config.yml")).getCreatedConfiguration();
			PartyCommand.getInstance().addCommand(new PartyJump(config.getStringList("Commands.Party.Jump.Names"),
					config.getInt("Commands.Party.Jump.Priority"),
					config.getString("Messages.Party.Jump.Help"), config.getString("Commands.Party.Jump.Permission"),
					config));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reload() {
		onEnable();
	}
}
