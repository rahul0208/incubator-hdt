package org.apache.hdt.test.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public abstract class TestUtils {

	public static IProject createProject() throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject tempProj = root.getProject("TempPrj"
				+ System.currentTimeMillis());
		tempProj.create(null);
		tempProj.open(null);
		return tempProj;
	}

	public static IJavaProject setJavaNature(IProject project) throws Exception {
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		project.setDescription(description, null);
		return JavaCore.create(project);
	}

}