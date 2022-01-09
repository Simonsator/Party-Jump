package de.simonsator.partyandfriends.extensions.partyjump.configuration;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

/**
 * @author Simonsator
 * @version 1.0.0 16.04.17
 */
public class JumpConfig extends ConfigurationCreator {

	public JumpConfig(File pFile, PAFExtension pPlugin) throws IOException {
		super(pFile, pPlugin, true);
		readFile();
		loadDefaults();
		saveFile();
		process();
	}

	private void loadDefaults() {
		set("General.DisableAutoJumpOnServerSwitch", false);
		set("General.InformAboutLeaderServerSwitch", false);
		set("Commands.Party.Jump.Names", "jump", "warp");
		set("Commands.Party.Jump.Permission", "");
		set("Commands.Party.Jump.Priority", 550);
		set("Messages.Party.Jump.Help", "&8/&5Party jump  &8- &7Teleports you to the server the leader is on");
		set("Messages.Party.Jump.Leader", " &cYou are the leader of the party. You cannot jump to yourself.");
		set("Messages.Party.Jump.AlreadyOnServer", " &cYou are already on the server.");
		set("Messages.Party.Jump.JumpedTo", " &6You jumped to the party leader.");
		set("Messages.Party.LeaderChangedServer", " &6The leader jumped to the server [SERVER].");
	}
}
