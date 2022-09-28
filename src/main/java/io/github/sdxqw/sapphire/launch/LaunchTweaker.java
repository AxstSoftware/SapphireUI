package io.github.sdxqw.sapphire.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.MixinEnvironment.Side;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class LaunchTweaker implements ITweaker {

	private static final List<String> args = new ArrayList<>();

	@Override
	public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
		LaunchTweaker.args.addAll(args);
		if(gameDir != null) {
			LaunchTweaker.args.add("--gameDir");
			LaunchTweaker.args.add(gameDir.getAbsolutePath());
		}
		if(assetsDir != null) {
			LaunchTweaker.args.add("--assetsDir");
			LaunchTweaker.args.add(assetsDir.getAbsolutePath());
		}
		if(profile != null) {
			LaunchTweaker.args.add("--version");
			LaunchTweaker.args.add(profile);
		}
	}

	@Override
	public void injectIntoClassLoader(LaunchClassLoader classLoader) {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.sapphire.json");

		MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();

		if(environment.getObfuscationContext() == null) {
			environment.setObfuscationContext("notch");
		}

		environment.setSide(Side.CLIENT);
	}

	@Override
	public String getLaunchTarget() {
		return MixinBootstrap.getPlatform().getLaunchTarget();
	}

	@Override
	public String[] getLaunchArguments() {
		return LaunchTweaker.args.toArray(new String[0]);
	}

}
