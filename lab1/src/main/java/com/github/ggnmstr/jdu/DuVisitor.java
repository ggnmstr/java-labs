package com.github.ggnmstr.jdu;

import com.github.ggnmstr.jdu.model.DuDirectory;
import com.github.ggnmstr.jdu.model.DuRegular;
import com.github.ggnmstr.jdu.model.DuSymlink;
import com.github.ggnmstr.jdu.model.DuUnknown;

public interface DuVisitor {
    void visit(DuSymlink symlink);
    void visit(DuDirectory directory);
    void visit(DuRegular regular);

    void visit(DuUnknown unknown);
}
