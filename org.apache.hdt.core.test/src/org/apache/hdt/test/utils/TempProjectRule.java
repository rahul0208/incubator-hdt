package org.apache.hdt.test.utils;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.rules.ExternalResource;

public class TempProjectRule extends ExternalResource {

	private IJavaProject javaProject;
	private IProject project;
	private IPackageFragment srcPackage;
	private IType mainClass;

	public IJavaProject getJavaProject() {
		return javaProject;
	}

	public IProject getProject() {
		return project;
	}

	public IType getMainClass() {
		return mainClass;
	}

	@Override
	protected void before() throws Throwable {
		createProject();
		setJavaNature();
		createPackage();
		createType();
	}

	@Override
	protected void after() {
		try {
			project.close(null);
			project.delete(true, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void createProject() throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject("TempPrj" + System.currentTimeMillis());
		project.create(new NullProgressMonitor());
		project.open(new NullProgressMonitor());
	}

	private void setJavaNature() throws Exception {
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		project.setDescription(description, null);
		javaProject = JavaCore.create(project);
	}

	private void createType() throws JavaModelException {
		String className = "TestClass";
		StringBuilder srcCode = new StringBuilder();
		srcCode.append("package " + srcPackage.getElementName() + ";\n");
		srcCode.append("\n");
		srcCode.append("public class " + className + "{\n"
				+ "public static void main(String[] args) {}}");
		ICompilationUnit cu = srcPackage.createCompilationUnit(className
				+ ".java", srcCode.toString(), false, null);
		mainClass = cu.getTypes()[0];
	}

	private void createPackage() throws CoreException {
		IFolder folder = project.getFolder("test-src");
		folder.create(false, true, null);
		IPackageFragmentRoot srcFolder = javaProject
				.getPackageFragmentRoot(folder);
		IClasspathEntry[] newClasspath = new IClasspathEntry[1];
		newClasspath[0] = JavaCore.newSourceEntry(srcFolder.getPath());
		javaProject.setRawClasspath(newClasspath, null);
		srcPackage = srcFolder.createPackageFragment("org.apache.hdt.test",
				false, null);
	}

}