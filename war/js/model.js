function GetUnity() {
	if (typeof unityObject != "undefined") {
		return unityObject.getObjectById("unityPlayer");
	}
	return null;
}
if (typeof unityObject != "undefined") {
	unityObject.embedUnity("unityPlayer", "/WebBuild.unity3d?id="+ drawingId, 980, 600);
}