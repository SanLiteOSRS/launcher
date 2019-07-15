/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
package net.runelite.launcher;

import lombok.extern.slf4j.Slf4j;
import net.runelite.launcher.ui.ColorScheme;
import net.runelite.launcher.ui.FontManager;
import net.runelite.launcher.ui.SwingUtil;
import org.pushingpixels.substance.internal.SubstanceSynapse;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
class LauncherFrame extends JFrame
{
	private static final LauncherProperties PROPERTIES = new LauncherProperties();

	private final JPanel panel = new JPanel();
	private final JProgressBar progressBar = new JProgressBar();
	private JLabel messageLabel;
	private JLabel subMessageLabel;

	LauncherFrame()
	{
		SwingUtilities.invokeLater(this::initLayout);
	}

	private void initLayout()
	{
		SwingUtil.setupRuneLiteLookAndFeel();

		this.setTitle("SanLite");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(220, 280));
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);

		messageLabel = new JLabel("Checking for updates");
		subMessageLabel = new JLabel();

		// Main panel setup
		// To reduce substance's colorization (tinting)
		panel.putClientProperty(SubstanceSynapse.COLORIZATION_FACTOR, 1.0);
		panel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		final GridBagLayout layout = new GridBagLayout();
		layout.columnWeights = new double[] {1};
		layout.rowWeights = new double[] {1, 0, 0, 1, 0, 1};
		panel.setLayout(layout);

		// Logo
		synchronized (ImageIO.class)
		{
			try
			{
				final BufferedImage logo = ImageIO.read(LauncherFrame.class.getResourceAsStream("sanlite.png"));
				this.setIconImage(logo);

				final BufferedImage logoTransparent = ImageIO.read(LauncherFrame.class.getResourceAsStream("sanlite_transparent.png"));
				final GridBagConstraints logoConstraints = new GridBagConstraints();
				logoConstraints.anchor = GridBagConstraints.SOUTH;
				panel.add(new JLabel(new ImageIcon(logoTransparent.getScaledInstance(96, 96, Image.SCALE_SMOOTH))), logoConstraints);
			}
			catch (IOException e)
			{
				log.warn("Error loading logo", e);
			}
		}

		// Title
		final JLabel title = new JLabel("SanLite");
		final GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridy = 1;
		panel.add(title, titleConstraints);

		// SanLite version
		final JLabel sanliteVersion = new JLabel("Launcher version " + PROPERTIES.getVersion());
		sanliteVersion.setFont(FontManager.getRunescapeSmallFont());
		sanliteVersion.setForeground(sanliteVersion.getForeground().darker());
		final GridBagConstraints sanliteVersionConstraints = new GridBagConstraints();
		sanliteVersionConstraints.gridy = 2;
		panel.add(sanliteVersion, sanliteVersionConstraints);

		// Progress bar
		progressBar.setUI(new BasicProgressBarUI());
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		progressBar.setFont(FontManager.getRunescapeSmallFont());

		final GridBagConstraints progressConstraints = new GridBagConstraints();
		progressConstraints.insets = new Insets(10, 25, 10, 25);
		progressConstraints.fill = GridBagConstraints.HORIZONTAL;
		progressConstraints.gridy = 3;
		panel.add(progressBar, progressConstraints);

		// Main message
		messageLabel.setFont(FontManager.getRunescapeSmallFont());
		final GridBagConstraints messageConstraints = new GridBagConstraints();
		messageConstraints.gridy = 4;
		panel.add(messageLabel, messageConstraints);

		// Alternate message
		final GridBagConstraints subMessageConstraints = new GridBagConstraints();
		subMessageLabel.setForeground(subMessageLabel.getForeground().darker());
		subMessageLabel.setFont(FontManager.getRunescapeSmallFont());
		subMessageConstraints.gridy = 5;
		panel.add(subMessageLabel, subMessageConstraints);

		this.setContentPane(panel);
		pack();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	void progress(int bytes, int total)
	{
		if (total == 0)
		{
			return;
		}

		int percent = (int) (((float) bytes / (float) total) * 100f);
		progressBar.setString(percent + "%");
		progressBar.setValue(percent);
	}

	void updateMessageLabelText(String text)
	{
		SwingUtilities.invokeLater(() ->
		{
			if (text != null)
			{
				messageLabel.setText(text);
			}
		});
	}

	void updateSubMessageLabelText(String text)
	{
		SwingUtilities.invokeLater(() ->
		{
			if (text != null)
			{
				subMessageLabel.setText(text);
			}
		});
	}
}
