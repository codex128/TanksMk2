/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.assets;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author codex
 */
public class JsonAssetLoader implements AssetLoader {

    @Override
    public JsonValue load(AssetInfo assetInfo) throws IOException {
        return Json.parse(new InputStreamReader(assetInfo.openStream()));
    }
    
}
