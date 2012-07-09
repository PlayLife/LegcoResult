function GetUnity() {
	if (typeof unityObject != "undefined") {
		return unityObject.getObjectById("unityPlayer");
	}
	return null;
}
if (typeof unityObject != "undefined") {
	unityObject.embedUnity("unityPlayer", "/WebBuild.unity3d", 980, 600);
}