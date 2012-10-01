package br.com.droid.resources;

import br.com.droid.exception.NoContentException;
import br.com.droid.model.Cliente;
import br.com.droid.model.ClienteBusiness;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/cliente")
public class ClienteResource {
	
	@GET
	@Path("/buscarTodos")
	@Produces({MediaType.APPLICATION_JSON})
	public ArrayList<Cliente> selTodos() throws ClassNotFoundException{
		return new ClienteBusiness().buscarTodos();
	}

	@GET
	@Path("/buscarTodosGSON")
	@Produces({MediaType.APPLICATION_JSON})
	public String selTodosGSON() throws ClassNotFoundException{
		return new Gson().toJson(new ClienteBusiness().buscarTodos());
	}
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Cliente getCliente(@PathParam("id") int id) throws ClassNotFoundException{
		Cliente cliente = new ClienteBusiness().buscar(id);
		if(cliente == null)
			throw new NoContentException("Cliente não encontrado!");
		return cliente;
	}
	
	@GET
	@Path("/delete/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public String deleteCliente(@PathParam("id") int id) throws ClassNotFoundException{
		return new ClienteBusiness().deletar(id);
	}
	
	@POST
	@Path("/inserir")
	@Produces({ "text/xml", "application/json" })
	@Consumes("application/json")
	public String inserirCliente(Cliente cliente) throws ClassNotFoundException {
		return new ClienteBusiness().inserir(cliente);
	}
	
	@POST
	@Path("/inserirLista")
	@Produces({ "text/xml", "application/json" })
	@Consumes("application/json")
	public String inserirLista(String listaClientesJson) throws ClassNotFoundException {
		Gson gson = new Gson();
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		JsonParser parser = new JsonParser();
                JsonArray array = parser.parse(listaClientesJson).getAsJsonArray();
	    
	    for (int i = 0; i < array.size(); i++) {
	    	listaClientes.add(gson.fromJson(array.get(i), Cliente.class));
		}
		return new ClienteBusiness().inserirLista(listaClientes);
	}
}
