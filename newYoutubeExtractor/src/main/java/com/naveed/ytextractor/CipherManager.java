package com.naveed.ytextractor;
import com.naveed.ytextractor.utils.HTTPUtility;
import com.naveed.ytextractor.utils.LogUtils;
import com.naveed.ytextractor.utils.RegexUtils;
import java.io.IOException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class CipherManager {
	
	private static final String RegexDesipherFunctionCode="\\{[a-zA-Z]{1,}=[a-zA-Z]{1,}\\.split\\(\"\"\\).*?\\};";
	private static final String RegexVarName="[a-zA-Z0-9$]{2}\\.[a-zA-Z0-9$]{2}\\([a-zA-Z]\\,(\\d\\d|\\d)\\)";
	private static  String RegexFindVarCode="";
	private static String cachedDechiperFunction=null;
	

	
	private  static String getDecipherCode(String Basejs) {

		String DechipherCode;
		String RawName;
		String DecipherFun="decipher=function(a)" + RegexUtils.matchGroup(RegexDesipherFunctionCode, Basejs);
		if (RegexUtils.matchGroup(RegexVarName, DecipherFun) != null){
			RawName=RegexUtils.matchGroup(RegexVarName, DecipherFun).replace("$","\\$");
		}
		else {
			RawName="";
		}
		String RealVarName=RawName.split("\\.")[0];
		RegexFindVarCode = "var\\s" + RealVarName + "=.*?\\};";	// Word 1
		String varCode=RegexUtils.matchGroup(RegexFindVarCode, Basejs);
		DechipherCode = DecipherFun + "\n" + varCode;
		LogUtils.log(DechipherCode);
		return DechipherCode;

	}

	
	public  static String dechiperSig(String sig,String playerUrl) throws IOException{
		if(cachedDechiperFunction==null){
			cachedDechiperFunction=getDecipherCode(getPlayerCode(playerUrl));
		}
		
		return RhinoEngine(sig);
	}
	
	
	
	private static String getPlayerCode(String playerUrl) throws IOException {

   
        return HTTPUtility.downloadPageSource(playerUrl);
       
    }

	



	private static String RhinoEngine(String s) {
		
		Context rhino = Context.enter();
		rhino.setOptimizationLevel(-1);
		try {
			Scriptable scope = rhino.initStandardObjects();
			rhino.evaluateString(scope, cachedDechiperFunction, "JavaScript", 1, null);
			Object obj = scope.get("decipher", scope);

			if (obj instanceof Function) {
				Function jsFunction = (Function) obj;
				Object jsResult = jsFunction.call(rhino, scope, scope, new Object[]{s});
				String result = Context.toString(jsResult);
				return result ;
			}
		}
		finally {
			Context.exit();
		}
		
		return s;
	}

	
	
	
	
}
