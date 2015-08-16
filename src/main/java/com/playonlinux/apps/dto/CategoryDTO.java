/*
 * Copyright (C) 2015 PÂRIS Quentin
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.playonlinux.apps.dto;

import com.playonlinux.core.dto.DTO;

import java.util.List;

/**
 * Represents a category of application
 */
public class CategoryDTO implements DTO {

    int id;
    CategoryType type;
    String name;
    List <ApplicationDTO> applications;
    
    public enum CategoryType {
        INSTALLERS,
        FUNCTIONS
    }

    public int getId() {
        return id;
    }

    public CategoryType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<ApplicationDTO> getApplications() {
        return applications;
    }
}
