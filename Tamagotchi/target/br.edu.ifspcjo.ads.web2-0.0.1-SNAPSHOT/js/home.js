var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
      var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
      })

      var myModal = document.getElementById('myModal');
      var bsModal = new bootstrap.Modal(document.getElementById('myModal'));
      
      myModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var id = button.getAttribute('data-bs-id');

        var modalTitle = myModal.querySelector('.modal-title')
        var modalButton = myModal.querySelector('.modal-footer #delete');

        modalTitle.textContent = 'Exclusão do Animal ' + id;
        
        // Usando onclick para garantir que a função deleteAnimal receba o botão correto da tabela
        modalButton.onclick = function(){
              deleteAnimal(button, id);
              bsModal.hide();
          };
      });

     // FUNÇÃO FETCH API ATUALIZADA
    

			
			  function deleteAnimal(button, id) {
			  			// Encontra a linha da tabela subindo os níveis (button -> span -> td -> tr)
			  			var row = button.parentNode.parentNode.parentNode; 
			  			
			  			// URL do Servlet de Animais
			  			const url = "animalRegisterServlet?action=delete&animal-id=" + id;

			  			// Solicitação GET.
			  			fetch(url)
			  				// Tratamento do sucesso
			  				.then(response => {
			  					return response.json(); // converter para JSON
			  				})
			  				.then(data => {
			  					if (data) {
			  						row.parentNode.removeChild(row); // remover linha da tabela
			  					}
			  				})
			  				.catch(error => console.log('Erro de solicitação', error)); // lidar com os erros por catch
			  		}