class Type < ActiveRecord::Base
	    has_one :clickertype
		validates :name,  presence: true, uniqueness: true
		validates :clickertype,  presence: true, uniqueness: true

end
