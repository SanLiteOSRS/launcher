/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.launcher.ui;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.util.Enumeration;

/**
 * Various Swing utilities.
 */
@Slf4j
public class SwingUtil
{
	private static boolean lookAndFeelIsSet = false;

	/**
	 * Sets some sensible defaults for swing.
	 * IMPORTANT! Needs to be called before main frame creation
	 */
	private static void setupDefaults()
	{
		// Force heavy-weight popups/tooltips.
		// Prevents them from being obscured by the game applet.
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setInitialDelay(300);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("MenuItem.foreground", Color.WHITE);
		UIManager.put("Panel.background", ColorScheme.DARK_GRAY_COLOR);
		UIManager.put("TextField.selectionBackground", ColorScheme.BRAND_BLUE_TRANSPARENT);
		UIManager.put("TextField.selectionForeground", Color.WHITE);
		UIManager.put("FormattedTextField.selectionBackground", ColorScheme.BRAND_BLUE_TRANSPARENT);
		UIManager.put("FormattedTextField.selectionForeground", Color.WHITE);
		UIManager.put("TextArea.selectionBackground", ColorScheme.BRAND_BLUE_TRANSPARENT);
		UIManager.put("TextArea.selectionForeground", Color.WHITE);
		UIManager.put("ProgressBar.background", ColorScheme.BRAND_BLUE_TRANSPARENT.darker());
		UIManager.put("ProgressBar.foreground", ColorScheme.BRAND_BLUE);
		UIManager.put("ProgressBar.selectionBackground", ColorScheme.BRAND_BLUE);
		UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
		UIManager.put("ProgressBar.border", new EmptyBorder(0, 0, 0, 0));
		UIManager.put("ProgressBar.verticalSize", new Dimension(16, 10));
		UIManager.put("ProgressBar.horizontalSize", new Dimension(10, 16));
		UIManager.put("ProgressBarUI", BasicProgressBarUI.class.getName());

		// Do not render shadows under popups/tooltips.
		// Fixes black boxes under popups that are above the game applet.
		System.setProperty("jgoodies.popupDropShadowEnabled", "false");

		// Do not fill in background on repaint. Reduces flickering when
		// the applet is resized.
		System.setProperty("sun.awt.noerasebackground", "true");
	}

	/**
	 * Sets default Swing font.
	 * IMPORTANT! Needs to be called before main frame creation
	 *
	 * @param font the new font to use
	 */
	private static void setFont(@Nonnull final Font font)
	{
		final FontUIResource f = new FontUIResource(font);
		final Enumeration keys = UIManager.getDefaults().keys();

		while (keys.hasMoreElements())
		{
			final Object key = keys.nextElement();
			final Object value = UIManager.get(key);

			if (value instanceof FontUIResource)
			{
				UIManager.put(key, f);
			}
		}
	}

	/**
	 * Sets up the RuneLite look and feel. Checks to see if the look and feel
	 * was already set up before running in case the splash screen has already
	 * set up the theme.
	 * This must be run inside the Swing Event Dispatch thread.
	 */
	public static void setupRuneLiteLookAndFeel()
	{
		if (!lookAndFeelIsSet)
		{
			lookAndFeelIsSet = true;
			// Set some sensible swing defaults
			SwingUtil.setupDefaults();

			// Use custom UI font
			SwingUtil.setFont(FontManager.getRunescapeFont());
		}
	}
}
