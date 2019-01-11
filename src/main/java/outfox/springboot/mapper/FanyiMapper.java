package outfox.springboot.mapper;

import outfox.springboot.entity.Fanyi;


public interface FanyiMapper {
    Fanyi findFirstLine();
    void deleteFanyiByID(int id);
    void insert(Fanyi fanyi);
    void updateByID(Fanyi fanyi);
}
