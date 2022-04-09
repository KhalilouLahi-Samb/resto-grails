package restaurant



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RestoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Resto.list(params), model:[restoInstanceCount: Resto.count()]
    }

    def show(Resto restoInstance) {
        respond restoInstance
    }

    def create() {
        respond new Resto(params)
    }

    @Transactional
    def save(Resto restoInstance) {
        if (restoInstance == null) {
            notFound()
            return
        }

        if (restoInstance.hasErrors()) {
            respond restoInstance.errors, view:'create'
            return
        }

        restoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'resto.label', default: 'Resto'), restoInstance.id])
                redirect restoInstance
            }
            '*' { respond restoInstance, [status: CREATED] }
        }
    }

    def edit(Resto restoInstance) {
        respond restoInstance
    }

    @Transactional
    def update(Resto restoInstance) {
        if (restoInstance == null) {
            notFound()
            return
        }

        if (restoInstance.hasErrors()) {
            respond restoInstance.errors, view:'edit'
            return
        }

        restoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Resto.label', default: 'Resto'), restoInstance.id])
                redirect restoInstance
            }
            '*'{ respond restoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Resto restoInstance) {

        if (restoInstance == null) {
            notFound()
            return
        }

        restoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Resto.label', default: 'Resto'), restoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'resto.label', default: 'Resto'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
