/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 JGTK
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package com.gitlab.ccook.jgtk;


import java.util.Objects;

public class MnemonicLabel {
    private String mnemonicLabel;

    /**
     * @param label The text of the label, with an underscore in front of the mnemonic character.
     */
    public MnemonicLabel(String label) {
        this.mnemonicLabel = label;
        if (!label.contains("_")) {
            this.mnemonicLabel = "_" + label;
        }
    }

    public String getMnemonicLabel() {
        return mnemonicLabel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mnemonicLabel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MnemonicLabel that = (MnemonicLabel) o;
        return mnemonicLabel.equals(that.mnemonicLabel);
    }
}
