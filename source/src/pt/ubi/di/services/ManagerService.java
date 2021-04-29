package pt.ubi.di.services;

import pt.ubi.di.model.Part;
import pt.ubi.di.utils.FileUtils;

import java.util.ArrayList;

public class ManagerService {

    public void registerPart(ArrayList<Part> parts, Part p) {
        parts.add(p);
        FileUtils.saveParts(parts);
    }
}
