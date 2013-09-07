package net.classicube.launcher;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientLauncher {

    private static final String ClassPath = "client.jar;libs/*",
            ClientClassPath = "com.oyasunadev.mcraft.client.core.ClassiCubeStandalone",
            JavaArgs = "-Dorg.lwjgl.util.Debug=true -Dsun.java2d.noddraw=true -Dsun.awt.noerasebackground=true -Dsun.java2d.d3d=false -Dsun.java2d.opengl=false -Dsun.java2d.pmoffscreen=false -Xmx800M";

    public static void launchClient() {
        LogUtil.getLogger().info("launchClient");
        ServerInfo server = SessionManager.serverDetails;
        final File java = getJavaPath();
        final ProcessBuilder processBuilder;
        
        String nativePath;
        try {
            nativePath = new File(PathUtil.getClientDir(), "natives").getCanonicalPath();
        } catch (IOException ex) {
            LogUtil.die(ex.toString());
            return;
        }

        processBuilder = new ProcessBuilder(
                java.getAbsolutePath(),
                "-cp",
                ClassPath,
                JavaArgs,
                "-Djava.library.path=" + nativePath,
                ClientClassPath,
                server.address.getHostAddress(),
                Integer.toString(server.port),
                SessionManager.getSession().account.PlayerName,
                server.hash,
                SessionManager.getSession().getSkinUrl());
        processBuilder.directory(PathUtil.getClientDir());
        processBuilder.inheritIO();

        try {
            LogUtil.getLogger().log(Level.INFO, concatStringsWSep(processBuilder.command(), " "));
            Process p = processBuilder.start();
            try {
                p.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerListScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        } catch (IOException ex) {
            LogUtil.die("Error launching client: " + ex);
        }
    }

    public static String concatStringsWSep(List<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String s : strings) {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.toString();
    }

    private static File getJavaPath() {
        return new File(System.getProperty("java.home"), "bin/java");
    }
}