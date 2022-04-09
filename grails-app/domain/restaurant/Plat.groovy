package restaurant

class Plat {
    Long id
    String nomPlat
    Double prixPlat

    static belongsTo = Resto

    static constraints = {
        nomPlat(nullable: false, blank: false)
        prixPlat(nullable: false)
    }
    static mapping = {
        table: 'plats'
        id column: 'id', generator: 'native', params: [sequence: 'plat_seq']
    }
}
